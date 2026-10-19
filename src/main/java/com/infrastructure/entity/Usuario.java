package com.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity


public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
    @Column(name= "data_de_nascimento", nullable = false)
    private LocalDateTime dataDeNascimento;
    @Column(name = "email", length = 100)
    private String email;
    @Column(name = "senha")
    private String senha;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Telefone> telefone;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Endereco> endereco;


}
