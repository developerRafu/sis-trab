package com.rafu.sistrab.rest.dto;

import java.math.BigDecimal;
import lombok.*;

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
