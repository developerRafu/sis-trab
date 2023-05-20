package com.rafu.sistrab.mappers;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.rest.dto.PontoRelatorioResponse;
import com.rafu.sistrab.rest.dto.PontoRequest;
import com.rafu.sistrab.rest.dto.PontoResponse;
import com.rafu.sistrab.vo.PontoRelatorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PontoMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    PontoResponse toResponse(final Ponto ponto);

    default Ponto toPonto(final PontoRequest pontoRequest) {
        if (pontoRequest == null) {
            return null;
        }

        Ponto.PontoBuilder ponto = Ponto.builder();

        ponto.tarefa(pontoRequest.getTarefa());
        ponto.descricao(pontoRequest.getDescricao());
        ponto.saldoHoras(pontoRequest.getSaldoHoras());
        ponto.isCalcHours(Objects.equals(pontoRequest.getIsCalcHours(), Boolean.TRUE));
        if (pontoRequest.getInicio() != null) {
            ponto.inicio(LocalDateTime.parse(pontoRequest.getInicio(), formatter));
        }
        if (pontoRequest.getFim() != null) {
            ponto.fim(LocalDateTime.parse(pontoRequest.getFim(), formatter));
        }

        return ponto.build();
    }

    PontoRelatorioResponse toRelatorioResponse(final PontoRelatorio result);
}
