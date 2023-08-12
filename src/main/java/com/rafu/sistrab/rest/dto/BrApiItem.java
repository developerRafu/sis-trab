package com.rafu.sistrab.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrApiItem {
    private BigDecimal regularMarketPrice;
    private String symbol;
}
