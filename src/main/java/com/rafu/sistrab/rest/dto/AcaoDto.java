package com.rafu.sistrab.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AcaoDto {
    private String bvmf;
    private BigDecimal dividendos;
    private Integer quantidade;
    private Integer vezesPagaAno;
    private BigDecimal preco;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal ganho;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal gasto;
}
