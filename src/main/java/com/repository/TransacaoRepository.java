package com.repository;

import com.infrastructure.entity.Conta;
import com.infrastructure.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByContaOrderByDataHoraDesc(Conta conta);
}
