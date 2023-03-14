package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Despesa;
import com.rafu.sistrab.domain.Renda;
import com.rafu.sistrab.repositories.DespesaRepository;
import com.rafu.sistrab.rest.dto.DespesaCalDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DespesaService {
  private final DespesaRepository repository;
  private final RendaService rendaService;

  public Despesa save(final Despesa Despesa) {
    return repository.save(Despesa);
  }

  public List<Despesa> findAll() {
    return repository.findAll();
  }

  public Optional<Despesa> findById(final Long id) {
    return repository.findById(id);
  }

  public DespesaCalDto calc() {
    final var rendaTotal =
        rendaService.findAll().stream()
            .map(Renda::getMontante)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    final var despesaTotal =
        repository.findAll().stream()
            .map(Despesa::getMontante)
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    final var economia = rendaTotal.subtract(despesaTotal);
    return DespesaCalDto.builder()
        .despesasTotais(despesaTotal)
        .rendaTotal(rendaTotal)
        .economiaTotal(economia)
        .build();
  }
}
