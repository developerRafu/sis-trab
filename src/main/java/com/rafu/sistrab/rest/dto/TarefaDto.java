package com.rafu.sistrab.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarefaDto {
    private Long id;
    private String descricao;
    private String codigo;
    private Long horas;
    private String evento;
    private String inicio;
    private String fim;
    private int atividadeId;
    private String tarefaMae;
    private String tipoTarefa;
    @JsonProperty(defaultValue = "true")
    private Boolean isCalcHours;
}
