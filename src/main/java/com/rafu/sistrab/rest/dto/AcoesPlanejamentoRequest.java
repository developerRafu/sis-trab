package com.rafu.sistrab.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AcoesPlanejamentoRequest {
    private BigDecimal montante;
    private List<AcoesRequest> acoes;
}
