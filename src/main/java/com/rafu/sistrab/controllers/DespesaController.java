package com.rafu.sistrab.controllers;

import com.rafu.sistrab.errors.NotFoundException;
import com.rafu.sistrab.mappers.DespesaMapper;
import com.rafu.sistrab.rest.dto.DespesaCalDto;
import com.rafu.sistrab.rest.dto.DespesaDto;
import com.rafu.sistrab.services.DespesaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("despesas")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DespesaController {
  private final DespesaService service;
  private final DespesaMapper mapper;

  @PostMapping
  public ResponseEntity<Void> post(@RequestBody DespesaDto dto) {
    final var result = service.save(mapper.toDespesa(dto));

    final var uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(result.getId())
            .toUri();

    return ResponseEntity.created(uri).build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<DespesaDto> getById(@PathVariable final Long id) {
    return service
        .findById(id)
        .map(result -> ResponseEntity.ok(mapper.toDespesaDto(result)))
        .orElseThrow(() -> new NotFoundException("Despesa"));
  }

  @GetMapping
  public ResponseEntity<List<DespesaDto>> getById() {
    return ResponseEntity.ok().body(service.findAll().stream().map(mapper::toDespesaDto).toList());
  }

  @GetMapping("/calc")
  public ResponseEntity<DespesaCalDto> calcDespesas() {
    return ResponseEntity.ok().body(service.calc());
  }
}
