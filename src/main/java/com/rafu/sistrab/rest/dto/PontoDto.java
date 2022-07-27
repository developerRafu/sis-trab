package com.rafu.sistrab.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PontoDto {
    private Long id;
    private LocalDateTime hora;
    private List<TarefaDto> tarefas;
    private Integer horasTrabalhadas;
}
