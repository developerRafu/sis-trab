package com.rafu.sistrab.rest.dto;

import com.rafu.sistrab.domain.Tarefa;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PontoDto {
    private Long id;
    private LocalDateTime hora;
    private List<Integer> codigo;
}
