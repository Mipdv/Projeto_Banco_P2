package com.controller;

import com.business.AcaoService;
import com.infrastructure.Client.AcaoClient;
import com.infrastructure.entity.BrapiResponse;
import com.infrastructure.entity.CotacaoAcaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/acoes")
public class AcaoController {

    private final AcaoService acaoService;

    @GetMapping("/{acao}")
    public ResponseEntity<CotacaoAcaoResponse> consulta(@PathVariable String acao){
        return ResponseEntity.ok(acaoService.buscar(acao));
    }
}
