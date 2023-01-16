package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.repositories.PontoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PontoServiceTest {

    PontoRepository repository;
    PontoService service;

    @BeforeEach
    void setUp() {
        repository = mock(PontoRepository.class);
        service = new PontoService(repository);
    }

    @Test
    void shouldSave() {
        final var ponto = getPonto();
        when(repository.save(any())).thenReturn(ponto);
        final var result = service.save(ponto);
        assertNotNull(result);
    }

    @Test
    void shouldCalculateDiff() {
        final var pontFound = getPonto();
        final var ponto = spy(getPontoWithHour(18, 0));
        when(ponto.getHora()).thenReturn(LocalDateTime.of(2023, 1, 16, 18, 0));
        when(repository.findByTarefa(any())).thenReturn(Optional.of(pontFound));
        final var result = service.save(ponto);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(6), result.getSaldoHoras());
    }

    @Test
    void shouldCalculateDiffWhenItsBreakHours() {
        final var pontFound = getPonto();
        final var ponto = spy(getPontoWithHour(18, 0));
        when(ponto.getHora()).thenReturn(LocalDateTime.of(2023, 1, 16, 18, 1));
        when(repository.findByTarefa(any())).thenReturn(Optional.of(pontFound));
        final var result = service.save(ponto);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(7), result.getSaldoHoras());
    }

    private Ponto getPonto() {
        return Ponto.builder()
                .id(1L)
                .descricao("Fazendo tarefa")
                .hora(LocalDateTime.of(2023, 1, 16, 12, 0))
                .tarefa("T-001")
                .saldoHoras(BigDecimal.ZERO)
                .taxa(BigDecimal.valueOf(70))
                .build();
    }

    private Ponto getPontoWithHour(final int hour, final int minute) {
        return Ponto.builder()
                .id(1L)
                .descricao("Fazendo tarefa")
                .hora(LocalDateTime.of(2023, 1, 16, hour, minute))
                .tarefa("T-001")
                .saldoHoras(BigDecimal.ZERO)
                .taxa(BigDecimal.valueOf(70))
                .build();
    }
}
