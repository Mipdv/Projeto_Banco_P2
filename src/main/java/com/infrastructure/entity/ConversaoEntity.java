//package com.infrastructure.entity;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Builder
//@Entity
//
//
//public class ConversaoEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne
//    @Column(name = "nome", length = 30)
//    private Usuario nome;
//    @Column(name = "moedaOrigem")
//    private BigDecimal moedaOrigem;
//    @Column(name = "moedaDestino")
//    private BigDecimal moedaDestino;
//    @Column(name = "valorOriginal")
//    private BigDecimal valorOriginal;
//    @Column(name = "valorConvertido")
//    private BigDecimal valorConvertido;
//    @Column(name = "cotacaoUsada")
//    private BigDecimal cotacaoUsada;
//    @Column(name = "dataHora")
//    private LocalDateTime dataHora;
//
//}
