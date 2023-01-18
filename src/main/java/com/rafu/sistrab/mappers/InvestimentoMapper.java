package com.rafu.sistrab.mappers;

import com.rafu.sistrab.domain.Investimento;
import com.rafu.sistrab.rest.dto.InvestimentoResponse;
import java.math.BigDecimal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvestimentoMapper {
  InvestimentoResponse toInvestimentoResponse(final Investimento investimento);

  default InvestimentoResponse toInvestimentoResponseWithTotal(
      final Investimento investimento, final BigDecimal total) {
    final var response = toInvestimentoResponse(investimento);
    response.setTotal(total);
    return response;
  }
}
