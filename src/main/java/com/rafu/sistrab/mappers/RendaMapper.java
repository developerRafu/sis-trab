package com.rafu.sistrab.mappers;

import com.rafu.sistrab.domain.Renda;
import com.rafu.sistrab.rest.dto.RendaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RendaMapper {
  Renda toRenda(final RendaDto dto);

  RendaDto toRendaDto(final Renda renda);
}
