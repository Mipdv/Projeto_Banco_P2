//package com.infrastructure.Client;
//
//import com.infrastructure.entity.CotacaoRequest;
//import com.infrastructure.entity.CotacaoResponse;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.Map;
//
//@FeignClient(name = "moeda", url = "https://economia.awesomeapi.com.br",
//        configuration = FeignClient.class)
//public interface ConversaoClient {
//    @GetMapping("https://economia.awesomeapi.com.br/json/last/{moedas}")
//    CotacaoResponse consultar(@PathVariable("moedas") String moedas,
//                              @RequestBody CotacaoRequest request);
//
//    Map<String, CotacaoResponse> consultar(String par);
//}
