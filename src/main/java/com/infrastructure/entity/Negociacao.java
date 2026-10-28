package com.infrastructure.entity;


import com.business.AcaoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Negociacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "conta")
    private Conta conta;
    @Column(name = "acao")
    private String acao;
    @Enumerated(EnumType.STRING)
    private AcaoEnum tipo;
    @Column(name = "quantidade")
    private Integer quantidade;
    @Column(name = "precoUnitario")
    private BigDecimal precoUnitario;
    @Column(name = "precoFinal", precision = 19, scale = 2)
    private BigDecimal precoFinal;
    @Column(name = "dataHora")
    private LocalDateTime dataHora;

}
