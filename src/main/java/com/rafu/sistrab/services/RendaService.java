package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Renda;
import com.rafu.sistrab.repositories.RendaRepository;
import com.rafu.sistrab.rest.dto.RendaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
