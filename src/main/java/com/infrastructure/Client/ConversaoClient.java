package com.infrastructure.Client;

import com.infrastructure.Client.configs.FeignConfig;
import com.infrastructure.entity.CotacaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "moeda", url = "https://economia.awesomeapi.com.br",
        configuration = FeignConfig.class)
public interface ConversaoClient {
    @GetMapping("/json/last/{moedas}")
    Map<String, CotacaoResponse> converterMoeda(@PathVariable("moedas")
                                                   String moedas);
}
