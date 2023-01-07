package com.rafu.sistrab.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Investimento {
    private Long meses;
    private BigDecimal valor;
    private BigDecimal valorInicial;
    private BigDecimal taxaMensal;
}
