package com.rafu.sistrab.mappers;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.domain.Tarefa;
import com.rafu.sistrab.rest.dto.PontoDto;
import com.rafu.sistrab.rest.dto.TarefaDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PontoMapper {
    PontoDto toDto(final Ponto ponto);

    default Ponto toEntity(final PontoDto dto) {
        Ponto ponto = convert(dto);

        if (dto.getTarefas() != null) {
            ponto.setTarefas(toTarefas(dto.getTarefas()));
        }

        return ponto;
    }

    Ponto convert(final PontoDto dto);

    List<Tarefa> toTarefas(final List<TarefaDto> dtos);
}
