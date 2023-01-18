package com.rafu.sistrab.rest.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PontoRelatorioResponse {
  private List<PontoResponse> pontos;
  private BigDecimal total;
}
