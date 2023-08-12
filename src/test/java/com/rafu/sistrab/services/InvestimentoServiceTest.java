package com.rafu.sistrab.services;

import static org.junit.jupiter.api.Assertions.*;

import com.rafu.sistrab.domain.Investimento;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.rafu.sistrab.mappers.AcoesMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class InvestimentoServiceTest {
    InvestimentoService service;
    AcoesMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(AcoesMapper.class);
        service = new InvestimentoService(mapper);
    }

    @Test
    void shouldReturnMontante() {
        final var investimento =
                Investimento.builder()
                        .meses(1L)
                        .valor(BigDecimal.valueOf(1000))
                        .taxaMensal(BigDecimal.valueOf(0.01))
                        .valorInicial(BigDecimal.ZERO)
                        .build();
        final var result = service.calculate(investimento);
        assertNotNull(result);
        assertEquals(new BigDecimal("1010.00"), result);
    }

    @Test
    void shouldReturnMontantInTwoMonths() {
        final var investimento =
                Investimento.builder()
                        .meses(2L)
                        .valor(BigDecimal.valueOf(1000))
                        .taxaMensal(BigDecimal.valueOf(0.01))
                        .valorInicial(BigDecimal.ZERO)
                        .build();
        final var result = service.calculate(investimento);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(2030.10).setScale(2, RoundingMode.HALF_EVEN), result);
    }

    @Test
    void shouldReturnMontantIn480Months() {
        final var investimento =
                Investimento.builder()
                        .meses(480L)
                        .valor(BigDecimal.valueOf(1000))
                        .taxaMensal(BigDecimal.valueOf(0.01))
                        .valorInicial(BigDecimal.ZERO)
                        .build();
        final var result = service.calculate(investimento);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(11882420.24).setScale(2, RoundingMode.HALF_EVEN), result);
    }
}
