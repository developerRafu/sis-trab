package com.rafu.sistrab.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RelatorioTarefa {
    private String codigo;
    private Long horas;
    private BigDecimal total;
    private String descricao;
    private String evento;
    private String inicio;
    private String fim;
    private String atividade;
}
