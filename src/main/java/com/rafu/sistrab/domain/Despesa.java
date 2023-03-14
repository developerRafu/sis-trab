package com.rafu.sistrab.domain;

import com.rafu.sistrab.domain.enums.DespesaTipo;
import com.rafu.sistrab.domain.enums.RendaTipo;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TAB_DESPESA")
public class Despesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal montante;
    @Enumerated(EnumType.STRING)
    private DespesaTipo tipo;
}
