package com.rafu.sistrab.domain;

import java.math.BigDecimal;
import lombok.*;

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
