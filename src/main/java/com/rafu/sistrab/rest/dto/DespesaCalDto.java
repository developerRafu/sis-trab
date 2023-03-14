package com.rafu.sistrab.rest.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DespesaCalDto {
    private BigDecimal rendaTotal;
    private BigDecimal despesasTotais;
    private BigDecimal economiaTotal;
}
