package com.rafu.sistrab.controllers;

import com.rafu.sistrab.mappers.PontoMapper;
import com.rafu.sistrab.rest.dto.PontoRelatorioResponse;
import com.rafu.sistrab.rest.dto.PontoRequest;
import com.rafu.sistrab.rest.dto.PontoResponse;
import com.rafu.sistrab.services.PontoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/pontos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PontoRestController {
    private final PontoService service;
    private final PontoMapper mapper;

    @PostMapping
    public ResponseEntity<PontoResponse> post(@RequestBody final PontoRequest request) {
        final var result = service.save(mapper.toPonto(request));
        return ResponseEntity.ok(mapper.toResponse(result));
    }

    @PostMapping("/multi")
    public ResponseEntity<List<PontoResponse>> postMulti(@RequestBody final List<PontoRequest> request) {
        final var result = service.saveAll(request.stream().map(mapper::toPonto).collect(Collectors.toList()));
        return ResponseEntity.ok().body(result.stream().map(mapper::toResponse).collect(Collectors.toList()));
    }

    @GetMapping
    public ResponseEntity<PontoRelatorioResponse> getAll() {
        final var result = service.buildRelaorio();
        return ResponseEntity.ok().body(mapper.toRelatorioResponse(result));
    }
}
