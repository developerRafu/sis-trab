package com.rafu.sistrab.controllers;

import com.rafu.sistrab.domain.Investimento;
import com.rafu.sistrab.mappers.InvestimentoMapper;
import com.rafu.sistrab.rest.dto.InvestimentoResponse;
import com.rafu.sistrab.services.InvestimentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/investimentos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InvestimentoRestController {
  private final InvestimentoService service;
  private final InvestimentoMapper mapper;

  @PostMapping
  public ResponseEntity<InvestimentoResponse> post(@RequestBody final Investimento investimento) {
    final var total = service.calculate(investimento);
    return ResponseEntity.ok(mapper.toInvestimentoResponseWithTotal(investimento, total));
  }
}
