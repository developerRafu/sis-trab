package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.repositories.PontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PontoService {
    private final PontoRepository repository;

    public Ponto save(final Ponto ponto) {
        ponto.setHora(LocalDateTime.now());
        final var pontoFound = repository.findByTarefa(ponto.getTarefa());
        if (pontoFound.isEmpty()) {
            ponto.setSaldoHoras(BigDecimal.ZERO);
            ponto.setTaxa(BigDecimal.valueOf(70));
            return repository.save(ponto);
        }
        final var minutes = ChronoUnit.MINUTES.between(pontoFound.get().getHora(), ponto.getHora());
        final var saldo = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), RoundingMode.UP);
        pontoFound.get().setHora(ponto.getHora());
        pontoFound.get().setSaldoHoras(saldo);
        repository.save(pontoFound.get());
        return pontoFound.get();
    }
}
