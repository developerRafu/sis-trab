package com.rafu.sistrab.rest.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PontoDto {
  private Long id;
  private LocalDateTime hora;
  private List<Integer> codigo;
}
