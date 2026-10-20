package com.infrastructure.entity;

import com.business.TipoTransacao;
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
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;
    @Column(name = "valor", precision = 19, scale = 2, nullable = false)
    private BigDecimal valor;
    @Column(name = "saldoApos", precision = 19, scale = 2)
    private BigDecimal saldoApos;
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;




}
