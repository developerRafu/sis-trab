package com.rafu.sistrab.controllers;

import com.rafu.sistrab.mappers.PontoMapper;
import com.rafu.sistrab.rest.dto.PontoDto;
import com.rafu.sistrab.services.PontoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
