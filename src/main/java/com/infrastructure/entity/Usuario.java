package com.infrastructure.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // recebe no cadastro, nunca devolve o hash
    private String senha;
    // Respostas do passo 2 do cadastro no front
    @Column(name = "objetivo")
    private String objetivo;
    @Column(name = "volume")
    private String volume;
    @Column(name = "perfil")
    private String perfil;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Telefone> telefone;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Endereco> endereco;


}
