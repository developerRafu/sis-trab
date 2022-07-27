package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.errors.NotFoundException;
import com.rafu.sistrab.repositories.PontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PontoService {
    private final PontoRepository repository;
    private final TarefaService tarefaService;

    public Page<Ponto> findAllPageable(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    public Optional<Ponto> findById(final Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Ponto insert(Ponto entity) {
        final var dataInicio = LocalDateTime.now().withMinute(0).withHour(0);
        final var dataFim = LocalDateTime.now().withMinute(59).withHour(23);
        final var pontosToday = repository.findAllToday(dataInicio, dataFim);
        int horasTrabalhadas = 0;

        if (!pontosToday.isEmpty()) {
            final var lastPonto = pontosToday.stream().min(Comparator.comparing(Ponto::getId));
            if(lastPonto.isPresent()) {
                horasTrabalhadas += LocalDateTime.now().getHour() - lastPonto.get().getHora().getHour();
            }
        }

        entity.setHora(LocalDateTime.now());
        repository.saveAndFlush(entity);
        saveTarefas(entity);
        entity.setHorasTrabalhadas(horasTrabalhadas);
        return entity;
    }

    private void saveTarefas(Ponto entity) {
        entity.getTarefas().forEach(t -> t.setPonto(entity));
        tarefaService.saveAll(entity.getTarefas());
    }

    public Ponto update(Ponto entity) {
        final var pontoFound = findById(entity.getId());

        if (pontoFound.isEmpty()) {
            throw new NotFoundException(Ponto.class.getName());
        }

        return repository.save(entity);
    }
}
