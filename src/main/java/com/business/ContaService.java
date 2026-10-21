package com.business;

import com.exceptions.ResoucerNotFoundException;
import com.infrastructure.entity.Conta;
import com.infrastructure.entity.Transacao;
import com.infrastructure.security.JwtUtil;
import com.repository.ContaRepository;
import com.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public Conta depositar(BigDecimal valor, String token){
        validaValor(valor);
        Conta conta = buscaContaDoToken(token);
        conta.setSaldo(conta.getSaldo().add(valor));
        registraTransacao(conta, TipoTransacao.DEPOSITO, valor);
        return contaRepository.save(conta);
    }

    @Transactional
    public Conta sacar(BigDecimal valor, String token){
        validaValor(valor);
        Conta conta = buscaContaDoToken(token);
        if(conta.getSaldo().compareTo(valor) < 0){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT, "Saldo insuficiente");
        }
        conta.setSaldo(conta.getSaldo().subtract(valor));
        registraTransacao(conta, TipoTransacao.SAQUE, valor);
        return contaRepository.save(conta);
    }

    private  void validaValor(BigDecimal valor){
        if(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor deve ser maior que zero");
        }
    }

    public BigDecimal consultarSaldo(String token){
        Conta conta = buscaContaDoToken(token);
        return conta.getSaldo();
    }

    public List<Transacao> consultarExtrato(String token){
        Conta conta = buscaContaDoToken(token);
        return transacaoRepository.findByContaOrderByDataHoraDesc(conta);
    }

    private Conta buscaContaDoToken(String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        return contaRepository.findByUsuarioEmail(email).orElseThrow(() ->
                new ResoucerNotFoundException("Conta não encontrada"));

    }

    private void registraTransacao(Conta conta,TipoTransacao tipo, BigDecimal valor){
        transacaoRepository.save(Transacao.builder()
                        .conta(conta)
                        .tipo(tipo)
                        .valor(valor)
                        .saldoApos(conta.getSaldo())
                        .dataHora(LocalDateTime.now())
                .build());
    }
}
