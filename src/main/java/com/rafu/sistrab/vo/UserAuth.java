package com.rafu.sistrab.vo;

import lombok.*;

import java.math.BigDecimal;

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
