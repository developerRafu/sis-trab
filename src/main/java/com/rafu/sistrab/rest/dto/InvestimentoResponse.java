package com.rafu.sistrab.rest.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvestimentoResponse {
    private Long meses;
    private BigDecimal valor;
    private BigDecimal valorInicial;
    private BigDecimal taxaMensal;
    private BigDecimal total;
}
