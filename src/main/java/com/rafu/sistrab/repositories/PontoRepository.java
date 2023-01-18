package com.rafu.sistrab.repositories;

import com.rafu.sistrab.domain.Ponto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PontoRepository extends JpaRepository<Ponto, Long> {
  Optional<Ponto> findByTarefa(String tarefa);
}
