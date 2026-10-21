//package com.infrastructure.entity;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
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
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "usuario_id")
//    private Usuario usuario;
//    @Column(name = "moedaOrigem")
//    private String moedaOrigem;
//    @Column(name = "moedaDestino")
//    private String moedaDestino;
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
