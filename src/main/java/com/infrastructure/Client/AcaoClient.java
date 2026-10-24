package com.infrastructure.Client;


import com.infrastructure.Client.configs.FeignConfig;
import com.infrastructure.entity.BrapiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "acao", url = "https://brapi.dev",
        configuration = FeignConfig.class)
public interface AcaoClient {

    @GetMapping("/api/quote/{acao}")
    BrapiResponse buscarCotacao(@PathVariable("acao") String acao,
                                @RequestParam("token") String token);


}
