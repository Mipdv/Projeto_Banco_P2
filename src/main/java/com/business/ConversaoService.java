//package com.business;
//
//import com.infrastructure.Client.ConversaoClient;
//import com.infrastructure.entity.CotacaoRequest;
//import com.infrastructure.entity.CotacaoResponse;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//
//public class ConversaoService {
//
//    private final ConversaoClient conversaoClient;
//    private final BancoService bancoService;
//
//
//
//    public BigDecimal converter(CotacaoRequest cotacaoRequest){
//        if(cotacaoRequest == null || cotacaoRequest.getMoedaOrigem() == null || cotacaoRequest.getMoedaDestino() == null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "informe a moeda de origem e o destino");
//            }
//        if(cotacaoRequest.getValor() == null || cotacaoRequest.getValor().compareTo(BigDecimal.ZERO) <= 0){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "valor deve ser maior que zero");
//        }
//        String par = cotacaoRequest.montaPar();
//        Map<String, CotacaoResponse> resposta = conversaoClient.consultar(par);
//
//        CotacaoResponse cotacaoResponse = resposta.get(par.replace("-", " "));
//
//        return cotacaoRequest.getValor().multiply(cotacaoResponse.getBid()).setScale(2, RoundingMode.HALF_EVEN);
//    }
//
//}
