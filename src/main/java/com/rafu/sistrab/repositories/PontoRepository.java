package com.rafu.sistrab.repositories;

import com.rafu.sistrab.domain.Ponto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PontoRepository extends JpaRepository<Ponto, Long> {
    @Query("SELECT p FROM Ponto p WHERE p.hora BETWEEN :inicio AND :fim")
    List<Ponto> findAllToday(
            @Param("inicio") LocalDateTime dataInicio,
            @Param("fim") LocalDateTime dataFim
    );
}
