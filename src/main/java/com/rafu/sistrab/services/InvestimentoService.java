package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.Investimento;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class InvestimentoService {
    public BigDecimal calculate(final Investimento investimento) {
        var montante = investimento.getValorInicial();
        for (long i = 0L; i < investimento.getMeses(); i++) {
            final var taxa = BigDecimal.ONE.add(investimento.getTaxaMensal());
            montante = montante.add(investimento.getValor()).multiply(taxa);
        }
        return montante.setScale(2, RoundingMode.HALF_EVEN);
    }
}
