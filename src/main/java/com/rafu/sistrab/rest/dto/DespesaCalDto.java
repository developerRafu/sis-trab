package com.rafu.sistrab.rest.dto;

import java.math.BigDecimal;
import lombok.*;

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
