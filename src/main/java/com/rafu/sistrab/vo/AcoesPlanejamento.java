package com.rafu.sistrab.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcoesPlanejamento {
    private BigDecimal montante;
    private BigDecimal restante;
    private List<Message> messages;
    private List<Acao> acoes;


}
