package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Ponto;
import com.rafu.sistrab.errors.InvalidPontoException;
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
    void shouldCalculateHours() {
        final var ponto = getPonto();
        when(repository.save(any())).thenReturn(ponto);
        final var result = service.save(ponto);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(4.0), result.getSaldoHoras());
        assertEquals(new BigDecimal("280.00"), result.getTotal());
    }

    @Test
    void shouldCalculateHoursWithMinute() {
        final var ponto = getPonto();
        ponto.setFim(LocalDateTime.now().plusHours(6).plusMinutes(1));
        when(repository.save(any())).thenReturn(ponto);
        final var result = service.save(ponto);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(4.0), result.getSaldoHoras());
        assertEquals(new BigDecimal("280.00"), result.getTotal());
    }

    @Test
    void shouldCalculateHoursPastDays() {
        final var ponto =  Ponto.builder()
                .id(1L)
                .descricao("Fazendo tarefa")
                .tarefa("T-001")
                .inicio(LocalDateTime.now())
                .fim(LocalDateTime.now().plusDays(1L))
                .saldoHoras(null)
                .build();

        when(repository.save(any())).thenReturn(ponto);
        final var result = service.save(ponto);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(4.0), result.getSaldoHoras());
        assertEquals(new BigDecimal("280.00"), result.getTotal());
    }

    @Test
    void shouldCalculateHours1DayAndHalf() {
        final var ponto = getPonto();
        ponto.setFim(LocalDateTime.now().plusDays(1L).plusHours(12L));
        when(repository.save(any())).thenReturn(ponto);
        final var result = service.save(ponto);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(8.0), result.getSaldoHoras());
        assertEquals(new BigDecimal("560.00"), result.getTotal());
    }

    @Test
    void shouldThrowExceptionWhenFimIsBeforeInicio() {
        final var ponto = getPonto();
        ponto.setFim(LocalDateTime.now().minusDays(2));
        when(repository.save(any())).thenReturn(ponto);
        assertThrows(InvalidPontoException.class, () -> service.save(ponto));
    }

    @Test
    void shouldAddIdWhenTarefaExists() {
        when(repository.findByTarefa(any())).thenReturn(Optional.of(getPonto()));
        when(repository.save(any())).thenReturn(getPonto());
        final var result = service.save(getPonto());
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(4.0), result.getSaldoHoras());
        assertEquals(new BigDecimal("280.00"), result.getTotal());
    }

    private Ponto getPonto() {
        return Ponto.builder()
                .id(1L)
                .descricao("Fazendo tarefa")
                .tarefa("T-001")
                .inicio(LocalDateTime.now())
                .fim(LocalDateTime.now().plusHours(8))
                .saldoHoras(null)
                .build();
    }
}
