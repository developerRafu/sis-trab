package com.rafu.sistrab.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PontoResponse {
  private Long id;
  private String tarefa;
  private String descricao;
  private BigDecimal saldoHoras;
  private LocalDateTime inicio;
  private LocalDateTime fim;
  private BigDecimal total;
}
