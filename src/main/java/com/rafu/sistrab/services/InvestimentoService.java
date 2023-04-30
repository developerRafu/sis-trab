package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Investimento;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.rafu.sistrab.rest.dto.AcaoDto;
import com.rafu.sistrab.rest.dto.AcoesAnosRequest;
import com.rafu.sistrab.rest.dto.AcoesResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class InvestimentoService {
    private static final BigDecimal QUANTIDATE_MESES = BigDecimal.valueOf(12);

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
}
