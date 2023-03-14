package com.rafu.sistrab.rest.dto;

import com.rafu.sistrab.domain.enums.RendaTipo;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RendaDto {
  private Long id;
  private BigDecimal montante;
  private RendaTipo tipo;
}
