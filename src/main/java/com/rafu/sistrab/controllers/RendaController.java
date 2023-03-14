package com.rafu.sistrab.controllers;

import com.rafu.sistrab.domain.Renda;
import com.rafu.sistrab.errors.NotFoundException;
import com.rafu.sistrab.mappers.RendaMapper;
import com.rafu.sistrab.rest.dto.RendaDto;
import com.rafu.sistrab.services.RendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("rendas")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RendaController {
    private final RendaService service;
    private final RendaMapper mapper;

    @PostMapping
    public ResponseEntity<Void> post(@RequestBody RendaDto dto) {
        final var result = service.save(mapper.toRenda(dto));

        final var uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(result.getId())
                        .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RendaDto> getById(@PathVariable final Long id) {
        return service.findById(id).map(result -> ResponseEntity.ok(mapper.toRendaDto(result))).orElseThrow(() -> new NotFoundException("Renda"));
    }

    @GetMapping
    public ResponseEntity<List<RendaDto>> getById() {
        return ResponseEntity.ok().body(service.findAll().stream().map(mapper::toRendaDto).toList());
    }
}
