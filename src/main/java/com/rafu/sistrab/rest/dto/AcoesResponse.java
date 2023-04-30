package com.rafu.sistrab.rest.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcoesResponse {
    private BigDecimal total;
    private List<AcaoDto> acoes;
}
