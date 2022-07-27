package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.repositories.PontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PontoService {
    private final PontoRepository repository;

    public Page<Ponto> findAllPageable(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    public Optional<Ponto> findById(final Long id) {
        return repository.findById(id);
    }
}
