package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Tarefa;
import com.rafu.sistrab.errors.NotFoundException;
import com.rafu.sistrab.repositories.TarefaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TarefaService {
    private final TarefaRepository repository;

    public Page<Tarefa> findAllPageable(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    public Optional<Tarefa> findById(final Long id) {
        return repository.findById(id);
    }

    public Tarefa insert(Tarefa entity) {
        return repository.save(entity);
    }

    public Tarefa update(Tarefa entity) {
        final var tarefaFound = findById(entity.getId());

        if (tarefaFound.isEmpty()) {
            throw new NotFoundException(Tarefa.class.getName());
        }

        return repository.save(entity);
    }
}
