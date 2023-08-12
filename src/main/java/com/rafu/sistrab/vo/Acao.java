package com.rafu.sistrab.vo;

import com.rafu.sistrab.vo.enums.Risco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Acao {
    private BigDecimal value;
    private String code;
    private String empresaNome;
    private Risco risco;
    private BigDecimal valorInvestido;
    private Integer quantidade;

    public BigDecimal getValorInvestido() {
        return valorInvestido.setScale(2, RoundingMode.HALF_EVEN);
    }
}
