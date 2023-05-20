package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.repositories.PontoRepository;
import com.rafu.sistrab.vo.PontoRelatorio;
import com.rafu.sistrab.vo.UserAuth;
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
    private final AuthService authService;

    public Ponto save(final Ponto ponto) {
        final var pontoId = repository.findByTarefa(ponto.getTarefa()).map(Ponto::getId).orElse(null);
        final var user = authService.loadUser();
        ponto.setId(pontoId);
        setTotal(ponto, user);
        return repository.save(ponto);
    }

    private static void setTotal(Ponto ponto, UserAuth user) {
        var saldoHoras = ponto.getSaldoHoras();
        if (ponto.isCalcHours()) {
            final var daysDiff = ChronoUnit.DAYS.between(ponto.getInicio(), ponto.getFim().plusDays(1L));
            saldoHoras =
                    BigDecimal.valueOf(daysDiff)
                            .multiply(ponto.getSaldoHoras());
        }
        ponto.setTotal(saldoHoras.multiply(user.getTaxa()));
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
