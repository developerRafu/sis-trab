package com.rafu.sistrab.rest.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PontoRequest {
    private String tarefa;
    private String descricao;
}
