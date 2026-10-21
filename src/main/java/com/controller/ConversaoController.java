package com.controller;


import com.business.ConversaoService;
import com.infrastructure.entity.CotacaoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conversao")
public class ConversaoController {

    private final ConversaoService conversaoService;

    @PostMapping
    public ResponseEntity<BigDecimal> conversao (@RequestBody CotacaoRequest cotacaoRequest){
        return ResponseEntity.ok(conversaoService.converter(cotacaoRequest));
    }

}
