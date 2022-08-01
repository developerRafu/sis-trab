package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Tarefa;
import com.rafu.sistrab.errors.NotFoundException;
import com.rafu.sistrab.repositories.TarefaRepository;
import com.rafu.sistrab.rest.dto.RelatorioTarefa;
import com.rafu.sistrab.rest.dto.RelatorioTarefasDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return repository
                .findByCodigo(entity.getCodigo())
                .map(tarefa -> {
                    tarefa.setDescricao(entity.getDescricao());
                    tarefa.setHoras(Math.addExact(tarefa.getHoras(), entity.getHoras()));
                    return repository.save(tarefa);
                })
                .orElse(repository.save(entity));
    }

    public Tarefa update(Tarefa entity) {
        final var tarefaFound = findById(entity.getId());

        if (tarefaFound.isEmpty()) {
            throw new NotFoundException(Tarefa.class.getName());
        }

        return repository.save(entity);
    }

    public List<Tarefa> saveAll(final List<Tarefa> tarefas) {
        final var codigos = tarefas.stream().map(Tarefa::getCodigo).collect(Collectors.toList());
        final var tarefasFound = repository.findAllByCodigo(codigos);

        final List<Tarefa> tarefasToSave = new ArrayList<>();

        tarefasToSave.addAll(tarefas);
        for (Tarefa tarefaToSave : tarefasToSave) {
            for (Tarefa tarefaFound : tarefasFound) {
                if (tarefaToSave.getCodigo().equals(tarefaFound.getCodigo())) {
                    tarefaToSave.setId(tarefaFound.getId());
                    tarefaToSave.setCodigo(tarefaFound.getCodigo());
                }
            }
        }


        return repository.saveAll(tarefasToSave);
    }

    public RelatorioTarefasDto getRelatorio() {
        final var tarefas = repository.findAll();
        final var relatorio = new RelatorioTarefasDto();

        final var total = tarefas.stream()
                .map(Tarefa::getHoras)
                .map(BigDecimal::valueOf)
                .map(value -> value.multiply(BigDecimal.valueOf(70)))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        relatorio.setTotal(total);

        relatorio.setHoras(tarefas.stream().mapToLong(Tarefa::getHoras).sum());

        final var relatoriosTarefas = tarefas.stream().map(tarefa -> {
            final var relatorioTarefa = new RelatorioTarefa();
            relatorioTarefa.setCodigo(tarefa.getCodigo());
            relatorioTarefa.setHoras(tarefa.getHoras().longValue());
            relatorioTarefa.setTotal(BigDecimal.valueOf(70).multiply(BigDecimal.valueOf(tarefa.getHoras())));
            relatorioTarefa.setDescricao(tarefa.getDescricao());
            relatorioTarefa.setInicio(tarefa.getInicio());
            relatorioTarefa.setFim(tarefa.getFim());
            relatorioTarefa.setEvento(tarefa.getEvento());
            relatorioTarefa.setAtividade(tarefa.getAtividade().getDescricao());
            return relatorioTarefa;
        }).collect(Collectors.toList());

        relatorio.setRelatorioTarefa(relatoriosTarefas);

        return relatorio;
    }

    public String getRelatorioPlano() {
        final var relatorio = getRelatorio();
        final var sb = new StringBuilder();
        for (RelatorioTarefa relatorioTarefa : relatorio.getRelatorioTarefa()) {
            sb.append("- Redmine ")
                    .append("#")
                    .append(relatorioTarefa.getCodigo())
                    .append(" - ")
                    .append(relatorioTarefa.getDescricao())
                    .append(String.format(" (%s a %s)", relatorioTarefa.getInicio(), relatorioTarefa.getFim()))
                    .append("\n");
        }
        return sb.toString();
    }

    public String getRelatorioAtividades() {
        final var relatorio = getRelatorio();
        final var sb = new StringBuilder();
        for (RelatorioTarefa relatorioTarefa : relatorio.getRelatorioTarefa()) {
            sb.append("- Redmine ")
                    .append("#")
                    .append(relatorioTarefa.getCodigo())
                    .append(" - ")
                    .append(relatorioTarefa.getEvento())
                    .append(" - ")
                    .append(relatorioTarefa.getAtividade())
                    .append("\n");
        }
        return sb.toString();
    }
}
