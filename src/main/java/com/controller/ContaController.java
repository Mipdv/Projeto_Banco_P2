package com.controller;


import com.business.ContaService;
import com.infrastructure.entity.Conta;
import com.infrastructure.entity.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conta")
public class ContaController {

    private final ContaService contaService;

    @PostMapping("/deposito")
    public ResponseEntity<Conta> deposito (@RequestHeader ("Authorization") String token,
                                           @RequestParam BigDecimal valor){
        return ResponseEntity.ok(contaService.depositar(valor, token));
    }

    @PostMapping("/saque")
    public ResponseEntity<Conta> saque(@RequestHeader ("Authorization") String token,
                                       @RequestParam BigDecimal valor){
        return ResponseEntity.ok(contaService.sacar(valor, token));
    }

    @GetMapping("/saldo")
    public ResponseEntity<BigDecimal> verSaldo(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(contaService.consultarSaldo(token));
    }

    @GetMapping("/extrato")
    public ResponseEntity<List<Transacao>> verExtrato(@RequestHeader ("Authorization") String token){
        return ResponseEntity.ok(contaService.consultarExtrato(token));
    }

}
