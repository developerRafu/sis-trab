package com.rafu.sistrab.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

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

  private String tarefa;
  private String descricao;
  private BigDecimal saldoHoras;
  private LocalDateTime inicio;
  private LocalDateTime fim;
  private BigDecimal total;
  @Transient
  private boolean isCalcHours;
}
