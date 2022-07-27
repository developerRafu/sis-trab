package com.rafu.sistrab.controllers;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.errors.NotFoundException;
import com.rafu.sistrab.mappers.PontoMapper;
import com.rafu.sistrab.rest.dto.PontoDto;
import com.rafu.sistrab.services.PontoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/pontos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PontoController {
    private final PontoService service;
    private final PontoMapper mapper;

    @GetMapping
    public ResponseEntity<Page<PontoDto>> getPage(@RequestParam(required = false) final int page) {
        final var pontos = service.findAllPageable(page).map(mapper::toDto);
        return ResponseEntity.ok().body(pontos);
    }

    @GetMapping("{id}")
    public ResponseEntity<PontoDto> getById(@PathVariable final Long id) {
        return service.findById(id)
                .map(p -> ResponseEntity.ok().body(mapper.toDto(p)))
                .orElseThrow(() -> new NotFoundException(Ponto.class.getName()));
    }

    @PostMapping
    public ResponseEntity<URI> post(@RequestBody final PontoDto dto) {
        final var entity = service.insert(mapper.toEntity(dto));

        final var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}").buildAndExpand(entity.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
