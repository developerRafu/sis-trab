package com.rafu.sistrab.domain;

import com.rafu.sistrab.domain.enums.RendaTipo;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TAB_RENDA")
public class Renda {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal montante;

  @Enumerated(EnumType.STRING)
  private RendaTipo tipo;
}
