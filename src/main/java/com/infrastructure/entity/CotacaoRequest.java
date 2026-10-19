package com.infrastructure.entity;

import lombok.*;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CotacaoRequest {
    private String moedaOrigem;   // ex.: USD

    private String moedaDestino;  // ex.: BRL

    private BigDecimal valor;     // quantia a converter na moeda de origem
    // Monta o par no formato esperado pela AwesomeAPI, ex.: USD-BRL
    public String montaPar() {
        return moedaOrigem.toUpperCase() + "-" + moedaDestino.toUpperCase();
    }
}
