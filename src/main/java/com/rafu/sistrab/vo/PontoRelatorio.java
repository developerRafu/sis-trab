package com.rafu.sistrab.vo;

import com.rafu.sistrab.domain.Ponto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PontoRelatorio {
    private List<Ponto> pontos;

    public BigDecimal getTotal() {
        return pontos.stream().map(Ponto::getTotal).reduce(BigDecimal::add).orElse(ZERO);
    }
}
