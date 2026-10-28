package com.controller;

import com.business.AcaoService;

import com.infrastructure.entity.CotacaoAcaoResponse;
import com.infrastructure.entity.Negociacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/acoes")
public class AcaoController {

    private final AcaoService acaoService;

    @GetMapping("/carteira")
    public ResponseEntity<Map<String, Object>> carteira(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(acaoService.carteira(token));
    }

    @GetMapping("/{acao}")
    public ResponseEntity<CotacaoAcaoResponse> consulta(@PathVariable String acao){
        return ResponseEntity.ok(acaoService.buscar(acao));
    }

    @PostMapping("/comprar")
    public ResponseEntity<Negociacao> comprar (@RequestHeader ("Authorization") String token,
                                               @RequestParam String acao,
                                               @RequestParam Integer quantidade){
        return ResponseEntity.ok(acaoService.comprar(token, acao, quantidade));
    }

    @PostMapping("/vender")
    public ResponseEntity<Negociacao> vender (@RequestHeader ("Authorization") String token,
                                              @RequestParam String acao,
                                              @RequestParam Integer quantidade){
        return ResponseEntity.ok(acaoService.vender(token, acao, quantidade));
    }
}
