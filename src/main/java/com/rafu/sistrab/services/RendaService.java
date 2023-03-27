package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Renda;
import com.rafu.sistrab.repositories.RendaRepository;
import com.rafu.sistrab.rest.dto.TetoDtoRequest;
import com.rafu.sistrab.rest.dto.TetoDtoResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RendaService {
  private final RendaRepository repository;

  public Renda save(final Renda renda) {
    return repository.save(renda);
  }

  public List<Renda> findAll() {
    return repository.findAll();
  }

  public Optional<Renda> findById(final Long id) {
    return repository.findById(id);
  }

  public TetoDtoResponse calcTeto(final TetoDtoRequest request) {
    final var montante =
        request.getNotes().stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    final var faltante = request.getValue().subtract(montante);
    return TetoDtoResponse.builder()
        .faltante(faltante)
        .total(montante)
        .value(request.getValue())
        .notes(request.getNotes())
        .recomendadoByMonth(
            faltante.divide(new BigDecimal(getMesesRestantes()), 2, RoundingMode.UP))
        .build();
  }

  private String getMesesRestantes() {
    final var dateNow = LocalDate.now();
    return 13 - dateNow.getMonthValue() + ".00";
  }
}
