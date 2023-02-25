package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.errors.InvalidPontoException;
import com.rafu.sistrab.repositories.PontoRepository;
import com.rafu.sistrab.vo.PontoRelatorio;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PontoService {
  private final PontoRepository repository;
  private final AuthService authService;

  public Ponto save(final Ponto ponto) {
    final var pontoId = repository.findByTarefa(ponto.getTarefa()).map(Ponto::getId).orElse(null);
    final var user = authService.loadUser();
    ponto.setId(pontoId);
    if (ponto.getSaldoHoras() != null) {
      ponto.setTotal(ponto.getSaldoHoras().multiply(user.getTaxa()));
      return repository.save(ponto);
    }
    final var diffDays = ChronoUnit.DAYS.between(ponto.getInicio(), ponto.getFim());
    if (diffDays < 0) {
      throw new InvalidPontoException();
    }
    var saldo = BigDecimal.valueOf(diffDays).multiply(user.getHorasMax());
    if (ponto.getFim().isAfter(ponto.getInicio().plusDays(diffDays))) {
      saldo = saldo.add(user.getHorasMax());
    }
    if (diffDays == 0) {
      saldo = user.getHorasMax();
    }
    var total = saldo.multiply(user.getTaxa());
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
