package com.rafu.sistrab.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RelatorioTarefa {
    private Integer codigo;
    private Long horas;
    private BigDecimal total;
    private String descricao;
}
