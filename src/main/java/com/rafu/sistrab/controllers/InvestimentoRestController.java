package com.rafu.sistrab.controllers;

import com.rafu.sistrab.domain.Investimento;
import com.rafu.sistrab.mappers.InvestimentoMapper;
import com.rafu.sistrab.rest.dto.AcoesAnosRequest;
import com.rafu.sistrab.rest.dto.AcoesPlanejamentoRequest;
import com.rafu.sistrab.rest.dto.AcoesResponse;
import com.rafu.sistrab.rest.dto.InvestimentoResponse;
import com.rafu.sistrab.services.InvestimentoService;
import com.rafu.sistrab.vo.AcoesPlanejamento;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;

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

    @PostMapping("/acoes")
    public ResponseEntity<AcoesResponse> postAcoes(@RequestBody final AcoesAnosRequest request) {
        return ResponseEntity.ok(service.calculateAcoes(request));
    }

    @PostMapping("/acoes/planejamento")
    public ResponseEntity<AcoesPlanejamento> postAcoes(@RequestBody final AcoesPlanejamentoRequest request) {
        final var result = service.calculatePlanejamento(request);
        result.getAcoes().sort(Comparator.comparing(a -> a.getRisco().getId()));
        return ResponseEntity.ok(result);
    }
}
