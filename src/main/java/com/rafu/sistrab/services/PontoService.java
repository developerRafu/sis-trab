package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.errors.InvalidPontoException;
import com.rafu.sistrab.repositories.PontoRepository;
import com.rafu.sistrab.vo.PontoRelatorio;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PontoService {
    private final PontoRepository repository;
    private final BigDecimal TAXA = BigDecimal.valueOf(70.0);
    private final BigDecimal HORAS_MAX_PER_DAY = BigDecimal.valueOf(4.0);

    public Ponto save(final Ponto ponto) {
        final var pontoId = repository.findByTarefa(ponto.getTarefa()).map(Ponto::getId).orElse(null);
        ponto.setId(pontoId);
        if (ponto.getSaldoHoras() != null) {
            ponto.setTotal(ponto.getSaldoHoras().multiply(TAXA));
            return repository.save(ponto);
        }
        final var diffDays = ChronoUnit.DAYS.between(ponto.getInicio(), ponto.getFim());
        if (diffDays < 0) {
            throw new InvalidPontoException();
        }
        var saldo = BigDecimal.valueOf(diffDays).multiply(HORAS_MAX_PER_DAY);
        if (ponto.getFim().isAfter(ponto.getInicio().plusDays(diffDays))) {
            saldo = saldo.add(HORAS_MAX_PER_DAY);
        }
        if (diffDays == 0) {
            saldo = HORAS_MAX_PER_DAY;
        }
        var total = saldo.multiply(TAXA);
        ponto.setSaldoHoras(saldo);
        ponto.setTotal(total);
        repository.save(ponto);
        return ponto;
    }

    public PontoRelatorio buildRelaorio() {
        final var pontos = repository.findAll();
        return PontoRelatorio.builder().pontos(pontos).build();
    }

    @Transactional
    public List<Ponto> saveAll(final List<Ponto> pontos) {
        return repository.saveAll(pontos.stream().peek(this::save).collect(Collectors.toList()));
    }
}
