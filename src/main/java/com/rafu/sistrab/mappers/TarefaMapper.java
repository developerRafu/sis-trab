package com.rafu.sistrab.mappers;

import static java.time.temporal.ChronoUnit.DAYS;

import com.rafu.sistrab.domain.Tarefa;
import com.rafu.sistrab.domain.enums.AtividadeEnum;
import com.rafu.sistrab.rest.dto.TarefaDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TarefaMapper {
    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    TarefaDto toDto(Tarefa tarefa);

    @Mapping(target = "inicio", ignore = true)
    @Mapping(target = "fim", ignore = true)
    Tarefa toEntity(TarefaDto dto);

    default Tarefa convert(TarefaDto dto) {
        final var entity = toEntity(dto);
        entity.setAtividade(AtividadeEnum.of(dto.getAtividadeId()));

        final var dateInicio = LocalDate.parse(getInicio(dto.getInicio()), FORMATTER);
        final var dateFim = LocalDate.parse(getInicio(dto.getFim()), FORMATTER);
        entity.setInicio(dateInicio);
        entity.setFim(dateFim);
        entity.setCalcHours(Objects.equals(dto.getIsCalcHours(), Boolean.TRUE));
        return entity;
    }

    private static String getInicio(String date) {
        return String.format("%s/%s", date, LocalDate.now().getYear());
    }

    default int getInt(String string) {
        return Integer.parseInt(string);
    }
}
