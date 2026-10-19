package com.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

// Representa uma cotação retornada pela AwesomeAPI (https://economia.awesomeapi.com.br/json/last/{moedas})
// A API devolve um objeto com a chave do par, ex.: {"USDBRL": {...}}, então use Map<String, CotacaoResponse>
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CotacaoResponse {

    private String code;        // moeda de origem, ex.: USD

    private String codein;      // moeda de destino, ex.: BRL

    private String name;        // ex.: Dólar Americano/Real Brasileiro

    private BigDecimal high;    // maior valor do dia

    private BigDecimal low;     // menor valor do dia

    private BigDecimal varBid;  // variação em relação ao fechamento anterior

    private BigDecimal pctChange; // variação percentual

    private BigDecimal bid;     // valor de compra

    private BigDecimal ask;     // valor de venda

    private Long timestamp;     // data da cotação em segundos (epoch)

    @JsonProperty("create_date")
    private String createDate;  // data da cotação, ex.: 2026-10-16 18:01:12
}
