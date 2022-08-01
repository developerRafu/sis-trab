package com.rafu.sistrab.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AtividadeEnum {
    BACK_END(0, "Codificar back-end"),
    FRONT_END(1, "Codificar-front"),
    BANCO_DE_DADOS(2, "Rodar scripts no banco de dados"),
    BACK_FRONT(3, "Codificar back-end e front-end");

    private final int id;
    private final String descricao;

    AtividadeEnum(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static AtividadeEnum of(int id) {
        return Arrays.stream(AtividadeEnum.values())
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Valor inválido"));
    }
}
