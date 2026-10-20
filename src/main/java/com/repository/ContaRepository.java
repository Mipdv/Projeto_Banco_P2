package com.repository;

import com.infrastructure.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional <Conta> findByUsuarioEmail(String email);


}
