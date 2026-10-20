package com.repository;

import com.infrastructure.entity.Conta;
import com.infrastructure.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    Conta buscaTransacaoPorConta(LocalDateTime data);
}
