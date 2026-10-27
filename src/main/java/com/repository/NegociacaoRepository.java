package com.repository;

import com.infrastructure.entity.Conta;
import com.infrastructure.entity.Negociacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NegociacaoRepository extends JpaRepository <Negociacao, Long> {
    @Query("""
    SELECT n.acao AS acao,
           SUM(CASE WHEN n.tipo = com.business.AcaoEnum.COMPRA
                    THEN n.quantidade ELSE -n.quantidade END) AS quantidade
    FROM Negociacao n
    WHERE n.conta = :conta
    GROUP BY n.acao
    HAVING SUM(CASE WHEN n.tipo = com.business.AcaoEnum.COMPRA
                    THEN n.quantidade ELSE -n.quantidade END) > 0
    """)
    List<PosicaoCarteira> buscarCarteira(@Param("conta") Conta conta);

    @Query("""
    SELECT COALESCE(SUM(CASE WHEN n.tipo = com.business.AcaoEnum.COMPRA
                             THEN n.quantidade ELSE -n.quantidade END), 0)
    FROM Negociacao n
    WHERE n.conta = :conta AND n.acao = :acao
    """)
    Long quantidadeDaAcao(@Param("conta") Conta conta, @Param("acao") String acao);
}
