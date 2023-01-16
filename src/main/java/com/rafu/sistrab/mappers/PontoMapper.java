package com.rafu.sistrab.mappers;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.rest.dto.PontoRequest;
import com.rafu.sistrab.rest.dto.PontoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PontoMapper {
    PontoResponse toResponse(final Ponto ponto);

    Ponto toPonto(final PontoRequest ponto);
}
