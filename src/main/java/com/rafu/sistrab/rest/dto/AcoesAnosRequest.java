package com.rafu.sistrab.rest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AcoesAnosRequest {
    private Integer anos;
    private List<AcaoDto> acoes;
}
