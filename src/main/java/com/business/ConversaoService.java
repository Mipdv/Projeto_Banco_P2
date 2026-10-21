package com.business;

import com.infrastructure.Client.ConversaoClient;
import com.infrastructure.entity.CotacaoRequest;
import com.infrastructure.entity.CotacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class ConversaoService {

    private final ConversaoClient conversaoClient;


    public BigDecimal converter(CotacaoRequest cotacaoRequest){
        if(cotacaoRequest == null || cotacaoRequest.getMoedaOrigem() == null || cotacaoRequest.getMoedaDestino() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "informe a moeda de origem e o destino");
            }
        if(cotacaoRequest.getValor() == null || cotacaoRequest.getValor().compareTo(BigDecimal.ZERO) <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "valor deve ser maior que zero");
        }
        String par = cotacaoRequest.montaPar();
        Map<String, CotacaoResponse> resposta = conversaoClient.converterMoeda(par);

        CotacaoResponse cotacaoResponse = resposta == null ? null : resposta.get(par.replace("-", ""));
        if(cotacaoResponse == null || cotacaoResponse.getBid() == null){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "cotação não encontrada para " + par);
        }

        return cotacaoRequest.getValor().multiply(cotacaoResponse.getBid()).setScale(2, RoundingMode.HALF_EVEN);
    }

}
