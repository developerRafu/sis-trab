package com.rafu.sistrab.rest.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Funcionalidades {
  private List<FuncionalidadeHoraDto> funcionalidades;

  public Integer getTotal() {
    return funcionalidades.stream().mapToInt(FuncionalidadeHoraDto::getPeriodo).sum();
  }
}
