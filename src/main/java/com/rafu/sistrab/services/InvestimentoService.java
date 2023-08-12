package com.rafu.sistrab.services;

import com.rafu.sistrab.clients.BrApiClient;
import com.rafu.sistrab.domain.Investimento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.rafu.sistrab.mappers.AcoesMapper;
import com.rafu.sistrab.rest.dto.*;
import com.rafu.sistrab.utils.NumberUtils;
import com.rafu.sistrab.vo.Acao;
import com.rafu.sistrab.vo.AcoesPlanejamento;
import com.rafu.sistrab.vo.Message;
import com.rafu.sistrab.vo.enums.Risco;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InvestimentoService {
    private static final BigDecimal QUANTIDATE_MESES = BigDecimal.valueOf(12.0);
    private static final BigDecimal TAXA_100_PORCENTO = BigDecimal.valueOf(100.0);
    private final AcoesMapper mapper;
    private final BrApiClient brApiClient;

    public BigDecimal calculate(final Investimento investimento) {
        var montante = investimento.getValorInicial();
        for (long i = 0L; i < investimento.getMeses(); i++) {
            final var taxa = BigDecimal.ONE.add(investimento.getTaxaMensal());
            montante = montante.add(investimento.getValor()).multiply(taxa);
        }
        return montante.setScale(2, RoundingMode.HALF_EVEN);
    }

    public AcoesResponse calculateAcoes(AcoesAnosRequest request) {
        final var anos = BigDecimal.valueOf(request.getAnos());
        request.getAcoes().forEach(acao -> {
            acao.setGanho(
                    BigDecimal.valueOf(acao.getVezesPagaAno())
                            .multiply(acao.getDividendos())
                            .multiply(anos)
                            .multiply(BigDecimal.valueOf(acao.getQuantidade()))
                            .multiply(QUANTIDATE_MESES)
            );
            acao.setGasto(
                    acao.getPreco()
                            .multiply(anos)
                            .multiply(BigDecimal.valueOf(acao.getQuantidade()))
                            .multiply(QUANTIDATE_MESES)
            );
        });
        final var total = request.getAcoes().stream().map(AcaoDto::getGanho).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return AcoesResponse.builder().acoes(request.getAcoes()).total(total).build();
    }

    public AcoesPlanejamento calculatePlanejamento(AcoesPlanejamentoRequest request) {
        final var brApiAcoes = getBrApiAcoesMap(request);
        final var planejamento = new AcoesPlanejamento();
        final var montante = request.getMontante();
        final var acoes = request.getAcoes().stream().map(mapper::toAcao).collect(Collectors.toList());
        final var totalPorcentagem = getTotalPorcentagem(acoes);
        handleInvalidPlanejamento(totalPorcentagem);
        final var restante = setValorInvestidoAcoes(acoes, montante, brApiAcoes);
        addMessage(montante, restante, planejamento, acoes);
        planejamento.setAcoes(acoes);
        planejamento.setMontante(montante);
        planejamento.setRestante(restante);
        realocar(restante, acoes, brApiAcoes);
        return planejamento;
    }

    private Map<String, BrApiItem> getBrApiAcoesMap(AcoesPlanejamentoRequest request) {
        final var codes = request.getAcoes().stream().map(AcoesRequest::getCode).collect(Collectors.joining(","));
        return brApiClient
                .getQuote(codes, "1d", "1d")
                .getResults().stream().collect(Collectors.toMap(BrApiItem::getSymbol, Function.identity()));
    }

    private void realocar(BigDecimal restante, List<Acao> acoes, Map<String, BrApiItem> brApiAcoes) {
        final var acoesRiscoBaixo = acoes.stream().filter(a -> a.getRisco().equals(Risco.BAIXO)).toList();
        final var valorPorAcao = restante.divide(BigDecimal.valueOf(acoesRiscoBaixo.size()), RoundingMode.HALF_EVEN);
        acoesRiscoBaixo.forEach(acao -> {
            acao.setValorInvestido(acao.getValorInvestido().add(valorPorAcao));
            setQuantidade(acao, acao.getValorInvestido(), brApiAcoes);
        });
    }

    private void addMessage(BigDecimal montante, BigDecimal restante, AcoesPlanejamento planejamento, List<Acao> acoes) {
        if (NumberUtils.isGreater(montante, restante) || NumberUtils.isEqual(montante, restante)) {
            planejamento.setMessages(getMessageInvestirAcoes(acoes, restante));
        }
    }

    private List<Message> getMessageInvestirAcoes(List<Acao> acoes, BigDecimal restante) {
        return List.of(Message.builder()
                .text(String.format("Houve sobra de %s.", NumberUtils.convertToBRL(restante)))
                .complemento("Foram divididos igualmente nas ações em details.")
                .details(acoes.stream().filter(a -> a.getRisco().equals(Risco.BAIXO)).map(Acao::getCode).collect(Collectors.toList()))
                .build());
    }

    private static BigDecimal setValorInvestidoAcoes(List<Acao> acoes, BigDecimal montante, Map<String, BrApiItem> brApiAcoes) {
        var restante = montante;
        for (Acao acao : acoes) {
            final var valorInvestido = getValorInvestido(acao, montante);
            acao.setValorInvestido(valorInvestido);
            setQuantidade(acao, valorInvestido, brApiAcoes);
            restante = restante.subtract(valorInvestido);
        }
        return restante;
    }

    private static void setQuantidade(Acao acao, BigDecimal valorInvestido, Map<String, BrApiItem> brApiAcoes) {
        final var brApiItem = brApiAcoes.get(acao.getCode());
        int quantidadeAcoes = valorInvestido.divide(brApiItem.getRegularMarketPrice(), 0, RoundingMode.HALF_UP).intValue();
        acao.setQuantidade(quantidadeAcoes);
    }

    private static BigDecimal getValorInvestido(Acao acao, BigDecimal montante) {
        return montante.multiply(acao.getRisco().getTaxa()).divide(TAXA_100_PORCENTO, RoundingMode.HALF_EVEN);
    }

    private static void handleInvalidPlanejamento(BigDecimal totalPorcentagem) {
        if (NumberUtils.isGreater(totalPorcentagem, BigDecimal.valueOf(100))) {
            throw new RuntimeException("A soma das porcentagens não pode ser maior que 100%");
        }
    }

    private static BigDecimal getTotalPorcentagem(List<Acao> acoes) {
        return acoes.stream().map(Acao::getRisco).map(Risco::getTaxa).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
