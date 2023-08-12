package com.rafu.sistrab.vo.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;

@Getter
public enum Risco {
    BAIXO(1, new BigDecimal("5.0")),
    MEDIO(2, new BigDecimal("2.5")),
    ALTO(3, new BigDecimal("1.0"));

    private final Integer id;
    private final BigDecimal taxa;

    Risco(final int value, BigDecimal taxa) {
        this.id = value;
        this.taxa = taxa;
    }

    public static Risco from(Integer id) {
        return Arrays.stream(Risco.values()).filter(risco -> risco.getId() == id).findFirst().orElse(null);
    }
}
