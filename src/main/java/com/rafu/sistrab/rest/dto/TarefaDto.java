package com.rafu.sistrab.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaDto {
    private Long id;
    private String descricao;
    private Integer codigo;
    private Integer horas;
    private String evento;
    private String inicio;
    private String fim;
    private int atividadeId;
}
