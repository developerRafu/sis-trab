package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Renda;
import com.rafu.sistrab.repositories.RendaRepository;
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
}
