package com.rafu.sistrab.mappers;

import com.rafu.sistrab.domain.Tarefa;
import com.rafu.sistrab.rest.dto.PontoDto;
import com.rafu.sistrab.rest.dto.TarefaDto;

public interface TarefaMapper {
    TarefaDto toDto(Tarefa tarefa);

    Tarefa toEntity(TarefaDto dto);
}
