package com.rafu.sistrab.rest.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PontoResponse {
  private Long id;
  private String tarefa;
  private String descricao;
  private BigDecimal saldoHoras;
  private String inicio;
  private String fim;
  private BigDecimal total;
  private Integer dias;
}
