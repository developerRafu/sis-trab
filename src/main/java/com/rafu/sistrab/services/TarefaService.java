package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Tarefa;
import com.rafu.sistrab.errors.NotFoundException;
import com.rafu.sistrab.repositories.TarefaRepository;
import com.rafu.sistrab.rest.dto.FuncionalidadeHoraDto;
import com.rafu.sistrab.rest.dto.RelatorioTarefa;
import com.rafu.sistrab.rest.dto.RelatorioTarefasDto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TarefaService {
    private final TarefaRepository repository;
    private final DocumentoService documentoService;
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
                .map(
                        tarefa -> {
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

        final List<Tarefa> tarefasToSave = new ArrayList<>(tarefas);

        for (Tarefa tarefaToSave : tarefasToSave) {
            for (Tarefa tarefaFound : tarefasFound) {
                if (tarefaToSave.getCodigo().equals(tarefaFound.getCodigo())) {
                    tarefaToSave.setId(tarefaFound.getId());
                    tarefaToSave.setCodigo(tarefaFound.getCodigo());
                }
            }
        }

        tarefasToSave.forEach(this::setHoras);

        return repository.saveAll(tarefasToSave);
    }

    private void setHoras(Tarefa tarefaToSave) {
        if (!tarefaToSave.isCalcHours()) {
            tarefaToSave.setHoras(tarefaToSave.getHoras());
            return;
        }
        tarefaToSave.setHoras(ChronoUnit.DAYS.between(tarefaToSave.getInicio(), tarefaToSave.getFim().plusDays(1L)) * 7L);
    }

    public RelatorioTarefasDto getRelatorio() {
        final var tarefas = repository.findAllByIgnoreFalse();
        final var relatorio = new RelatorioTarefasDto();

        final var total =
                tarefas.stream()
                        .map(Tarefa::getHoras)
                        .map(BigDecimal::valueOf)
                        .map(value -> value.multiply(BigDecimal.valueOf(70)))
                        .reduce(BigDecimal::add)
                        .orElse(BigDecimal.ZERO);

        relatorio.setTotal(total);

        relatorio.setHoras(tarefas.stream().mapToLong(Tarefa::getHoras).sum());

        final var relatoriosTarefas =
                tarefas.stream()
                        .map(
                                tarefa -> {
                                    final var relatorioTarefa = new RelatorioTarefa();
                                    relatorioTarefa.setCodigo(tarefa.getCodigo());
                                    relatorioTarefa.setHoras(tarefa.getHoras().longValue());
                                    relatorioTarefa.setTotal(
                                            BigDecimal.valueOf(70).multiply(BigDecimal.valueOf(tarefa.getHoras())));
                                    relatorioTarefa.setDescricao(tarefa.getDescricao());
                                    relatorioTarefa.setInicio(tarefa.getInicio().format(FORMATTER));
                                    relatorioTarefa.setFim(tarefa.getFim().format(FORMATTER));
                                    relatorioTarefa.setEvento(tarefa.getEvento());
                                    relatorioTarefa.setAtividade(tarefa.getAtividade().getDescricao());
                                    relatorioTarefa.setTarefaMae(tarefa.getTarefaMae());
                                    relatorioTarefa.setTipoTarefa(tarefa.getTipoTarefa());
                                    return relatorioTarefa;
                                })
                        .collect(Collectors.toList());

        relatorio.setRelatorioTarefa(relatoriosTarefas);

        return relatorio;
    }

    public String getRelatorioPlano() {
        final var relatorio = getRelatorio();
        final var sb = new StringBuilder();
        for (RelatorioTarefa relatorioTarefa : relatorio.getRelatorioTarefa()) {
            sb.append("- Jira ")
                    .append("#")
                    .append(relatorioTarefa.getCodigo())
                    .append(" - ")
                    .append(relatorioTarefa.getDescricao())
                    .append(
                            String.format(" (%s a %s)", relatorioTarefa.getInicio(), relatorioTarefa.getFim()))
                    .append("\n");
        }
        return sb.toString();
    }

    public String getRelatorioAtividades() {
        final var relatorio = getRelatorio();
        final var sb = new StringBuilder();
        for (RelatorioTarefa relatorioTarefa : relatorio.getRelatorioTarefa()) {
            sb.append("- Jira ")
                    .append("#")
                    .append(relatorioTarefa.getCodigo())
                    .append(" - ")
                    .append(relatorioTarefa.getDescricao())
                    .append(" - ")
                    .append(relatorioTarefa.getAtividade())
                    .append("\n");
        }
        return sb.toString();
    }

    public List<FuncionalidadeHoraDto> getFuncionalidadesPorHora() {
        final var result = repository.findAll();
        final List<FuncionalidadeHoraDto> lista = new ArrayList<>();
        for (final Tarefa tarefa : result) {
            final var funcionalidade =
                    FuncionalidadeHoraDto.builder()
                            .funcionalidade(tarefa.getEvento())
                            .periodo(tarefa.getHoras())
                            .tipoAtividade(tarefa.getAtividade().getType())
                            .build();
            if (lista.stream()
                    .anyMatch(o -> o.getFuncionalidade().equals(funcionalidade.getFuncionalidade()))) {
                lista.stream()
                        .filter(o -> o.getFuncionalidade().equals(funcionalidade.getFuncionalidade()))
                        .forEach(o -> updatePeriodo(o, funcionalidade));
            } else {
                lista.add(funcionalidade);
            }
        }
        return lista;
    }

    public byte[] getRelatorioXLS() {
        return documentoService.getRelatorio(repository.findAll()).toByteArray();
    }

    private void updatePeriodo(
            final FuncionalidadeHoraDto o, final FuncionalidadeHoraDto funcionalidade) {
        o.setPeriodo(o.getPeriodo() + funcionalidade.getPeriodo());
    }

    public String getRelatorioCSV() {
        return documentoService.getRelatorioCSV(getRelatorio());
    }

    public String getResponseCSV() {
        final var obj = getRelatorio();
        return obj.getRelatorioTarefa().stream().map(relatorioTarefa ->
                String.format("%s;%s;%s;%s;%s;%s", relatorioTarefa.getCodigo(), relatorioTarefa.getDescricao(), relatorioTarefa.getEvento(), relatorioTarefa.getTipoTarefa(), relatorioTarefa.getHoras(), relatorioTarefa.getHoras())
        ).collect(Collectors.joining("\n")) + "\nTotal:" + obj.getHoras();
    }
}
