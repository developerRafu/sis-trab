package com.rafu.sistrab.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PontoRelatorioResponse {
    private List<PontoResponse> pontos;
    private BigDecimal total;
}
