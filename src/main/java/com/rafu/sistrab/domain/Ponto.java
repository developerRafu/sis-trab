package com.rafu.sistrab.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TAB_PONTO")
@Getter
@Setter
public class Ponto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime hora;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ponto")
    private List<Tarefa> tarefas;
}
