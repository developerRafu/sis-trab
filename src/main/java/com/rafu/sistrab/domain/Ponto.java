package com.rafu.sistrab.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TAB_PONTO")
public class Ponto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime hora;
    private String tarefa;
    private String descricao;
    private BigDecimal saldoHoras;
    private BigDecimal taxa;
}