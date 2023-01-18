package com.rafu.sistrab.repositories;

import com.rafu.sistrab.domain.Tarefa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
  @Query("SELECT t FROM Tarefa t WHERE t.codigo IN (:codigos)")
  List<Tarefa> findAllByCodigo(@Param("codigos") List<String> codigos);

  Optional<Tarefa> findByCodigo(String codigo);
}
