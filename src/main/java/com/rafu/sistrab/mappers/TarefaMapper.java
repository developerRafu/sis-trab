package com.rafu.sistrab.mappers;

import com.rafu.sistrab.domain.Tarefa;
import com.rafu.sistrab.rest.dto.TarefaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaMapper {
    TarefaDto toDto(Tarefa tarefa);

    Tarefa toEntity(TarefaDto dto);
}
