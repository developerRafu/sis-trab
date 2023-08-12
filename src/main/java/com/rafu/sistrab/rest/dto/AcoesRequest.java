package com.rafu.sistrab.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AcoesRequest {
    private BigDecimal value;
    private String code;
    private String empresaNome;
    private Integer risco;
}
