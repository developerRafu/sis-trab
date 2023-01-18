package com.rafu.sistrab.rest.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PontoRequest {
    private String tarefa;
    private String descricao;
    private String inicio;
    private String fim;
    private BigDecimal saldoHoras;
}
