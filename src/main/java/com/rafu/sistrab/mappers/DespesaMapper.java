package com.rafu.sistrab.mappers;

import com.rafu.sistrab.domain.Despesa;
import com.rafu.sistrab.rest.dto.DespesaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DespesaMapper {
  Despesa toDespesa(final DespesaDto dto);

  DespesaDto toDespesaDto(final Despesa Despesa);
}
