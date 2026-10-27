package com.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;
    @Column(name = "saldo", precision = 19, scale = 2)
    private BigDecimal saldo;
    @Column(name = "limiteCartao", precision = 19, scale = 2)
    private BigDecimal limiteCartao;
    @Version
    private Long version;

}
