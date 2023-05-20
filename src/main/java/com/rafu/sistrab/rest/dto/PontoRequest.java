package com.rafu.sistrab.rest.dto;

import java.math.BigDecimal;
import lombok.*;

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
  private Boolean isCalcHours;
}
