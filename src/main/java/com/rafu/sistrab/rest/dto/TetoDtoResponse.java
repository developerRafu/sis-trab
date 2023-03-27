package com.rafu.sistrab.rest.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TetoDtoResponse {
  private BigDecimal value;
  private BigDecimal faltante;
  private BigDecimal total;
  private BigDecimal recomendadoByMonth;
  private List<BigDecimal> notes;
}
