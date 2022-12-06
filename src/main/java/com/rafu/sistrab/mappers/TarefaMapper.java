package com.rafu.sistrab.mappers;

import com.rafu.sistrab.domain.Tarefa;
import com.rafu.sistrab.domain.enums.AtividadeEnum;
import com.rafu.sistrab.rest.dto.TarefaDto;
import org.mapstruct.Mapper;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Mapper(componentModel = "spring")
public interface TarefaMapper {
    TarefaDto toDto(Tarefa tarefa);

    Tarefa toEntity(TarefaDto dto);

    default Tarefa convert(TarefaDto dto) {
        final var entity = toEntity(dto);
        entity.setAtividade(AtividadeEnum.of(dto.getAtividadeId()));
        final var inicio = dto.getInicio();
        final var fim = dto.getFim();
        final var dateInicio = LocalDate.of(LocalDate.now().getYear(), getInt(inicio.substring(3, 5)), getInt(inicio.substring(0, 2)));
        final var dateFim = LocalDate.of(LocalDate.now().getYear(), getInt(fim.substring(3, 5)), getInt(fim.substring(0, 2)));
        final long horas = DAYS.between(dateInicio, dateFim) * 4;
        entity.setHoras(Integer.parseInt(Long.toString(horas)));
        return entity;
    }

    default int getInt(String string) {
        return Integer.parseInt(string);
    }
}
