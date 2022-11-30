package com.rafu.sistrab.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Funcionalidades {
    private List<FuncionalidadeHoraDto> funcionalidades;

    public Integer getTotal() {
        return funcionalidades.stream().mapToInt(FuncionalidadeHoraDto::getPeriodo).sum();
    }
}
