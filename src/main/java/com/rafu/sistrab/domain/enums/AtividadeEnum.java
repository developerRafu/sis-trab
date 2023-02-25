package com.rafu.sistrab.domain.enums;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum AtividadeEnum {
  FEATURE(0, "Desenvolvimento de feature nova"),
  BANCO_DE_DADOS(1, "Rodar scripts no banco de dados"),
  RESOLUCAO_BUG(2, "Resolução de bug");

  private final int id;
  private final String descricao;

  AtividadeEnum(int id, String descricao) {
    this.id = id;
    this.descricao = descricao;
  }

  public static AtividadeEnum of(int id) {
    return Arrays.stream(AtividadeEnum.values())
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Valor inválido"));
  }

  public String getType() {
    if (this.id == 4) {
      return "Correção de bug";
    } else {
      return "Desenvolvimento";
    }
  }
}
