package com.rafu.sistrab.controllers;

import com.rafu.sistrab.mappers.PontoMapper;
import com.rafu.sistrab.rest.dto.PontoRequest;
import com.rafu.sistrab.rest.dto.PontoResponse;
import com.rafu.sistrab.services.PontoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
