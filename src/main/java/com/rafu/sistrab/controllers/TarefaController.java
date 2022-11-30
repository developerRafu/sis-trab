package com.rafu.sistrab.controllers;

import com.rafu.sistrab.domain.Tarefa;
import com.rafu.sistrab.errors.NotFoundException;
import com.rafu.sistrab.mappers.TarefaMapper;
import com.rafu.sistrab.rest.dto.FuncionalidadeHoraDto;
import com.rafu.sistrab.rest.dto.Funcionalidades;
import com.rafu.sistrab.rest.dto.RelatorioTarefasDto;
import com.rafu.sistrab.rest.dto.TarefaDto;
import com.rafu.sistrab.services.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/tarefas")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TarefaController {
    private final TarefaService service;
    private final TarefaMapper mapper;
    private static final String PATH_ID = "/{id}";

    @GetMapping
    public ResponseEntity<Page<TarefaDto>> getPage(@RequestParam(required = false) final int page) {
        final var tarefas = service.findAllPageable(page).map(mapper::toDto);
        return ResponseEntity.ok().body(tarefas);
    }

    @GetMapping("{id}")
    public ResponseEntity<TarefaDto> getById(@PathVariable @NotNull final Long id) {
        return service.findById(id)
                .map(p -> ResponseEntity.ok().body(mapper.toDto(p)))
                .orElseThrow(() -> new NotFoundException(Tarefa.class.getName()));
    }

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody final TarefaDto dto) {
        final var entity = service.insert(mapper.toEntity(dto));

        final var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(PATH_ID).buildAndExpand(entity.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> put(@PathVariable @NotNull Long id, @RequestBody TarefaDto dto) {
        dto.setId(id);
        final var entity = service.update(mapper.toEntity(dto));

        final var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(PATH_ID).buildAndExpand(entity.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("multi")
    public ResponseEntity<List<TarefaDto>> saveMulti(@RequestBody @Valid @NotNull List<TarefaDto> dtos) {
        final var tarefas = service.saveAll(dtos.stream().map(mapper::convert).collect(Collectors.toList()));
        return ResponseEntity.ok().body(tarefas.stream().map(mapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping("relatorio")
    public ResponseEntity<RelatorioTarefasDto> getRelatorio() {
        return ResponseEntity.ok().body(service.getRelatorio());
    }

    @GetMapping("relatorio/plano")
    public ResponseEntity<String> getRelatorioPlano() {
        return ResponseEntity.ok().body(service.getRelatorioPlano());
    }

    @GetMapping("relatorio/atividade")
    public ResponseEntity<String> getRelatorioAtividades() {
        return ResponseEntity.ok().body(service.getRelatorioAtividades());
    }

    @GetMapping("relatorio/funcionalidades")
    public ResponseEntity<Funcionalidades> getFuncionalidadesPorHora() {
        final var result = new Funcionalidades(service.getFuncionalidadesPorHora());
        return ResponseEntity.ok().body(result);
    }
}
