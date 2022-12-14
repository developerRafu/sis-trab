package com.rafu.sistrab.domain;

import com.rafu.sistrab.domain.enums.AtividadeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TAB_TAREFA")
@Getter
@Setter
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private String codigo;
    private Integer horas;
    private String evento;
    private String inicio;
    private String fim;
    private AtividadeEnum atividade;
}
