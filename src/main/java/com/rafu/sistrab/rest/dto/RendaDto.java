package com.rafu.sistrab.rest.dto;

import com.rafu.sistrab.domain.enums.RendaTipo;
import lombok.*;

import java.math.BigDecimal;

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
