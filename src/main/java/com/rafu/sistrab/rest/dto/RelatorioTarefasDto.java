package com.rafu.sistrab.rest.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioTarefasDto {
  private Long horas;
  private BigDecimal total;
  private List<RelatorioTarefa> relatorioTarefa;
}
