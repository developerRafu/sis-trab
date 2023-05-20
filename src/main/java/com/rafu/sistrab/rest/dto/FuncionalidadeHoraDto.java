package com.rafu.sistrab.rest.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "periodo")
public class FuncionalidadeHoraDto {
  private Long periodo;
  private String funcionalidade;
  private String tipoAtividade;
}
