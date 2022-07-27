package com.rafu.sistrab.repositories;

import com.rafu.sistrab.domain.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
