package com.rafu.sistrab.vo;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuth {
  private Long id;
  private String name;
  private String email;
  private BigDecimal taxa;
  private BigDecimal horasMax;
}
