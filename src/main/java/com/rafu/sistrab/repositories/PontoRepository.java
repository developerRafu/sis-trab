package com.rafu.sistrab.repositories;

import com.rafu.sistrab.domain.Ponto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PontoRepository extends JpaRepository<Ponto, Long> {
    Optional<Ponto> findByTarefa(String tarefa);
}
