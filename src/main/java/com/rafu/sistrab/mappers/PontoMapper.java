package com.rafu.sistrab.mappers;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.rest.dto.PontoDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface PontoMapper {
    PontoDto toDto(final Ponto ponto);
}
