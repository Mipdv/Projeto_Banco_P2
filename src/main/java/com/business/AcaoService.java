package com.business;


import com.infrastructure.Client.AcaoClient;
import com.infrastructure.entity.BrapiResponse;
import com.infrastructure.entity.CotacaoAcaoResponse;
import com.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AcaoService {
    private final JwtUtil jwtUtil;
    private final AcaoClient acaoClient;
    @Value("${brapi.token}")
    private String brapiToken;

    public CotacaoAcaoResponse buscar (String acao){
        if(acao == null || acao.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o tipo da ação");
        }
        String tipo = acao.trim().toUpperCase();
        if(!tipo.matches("[A-Z]{4}\\d{1,2}")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo inválido " + tipo);
        }
        BrapiResponse resposta = acaoClient.buscarCotacao(tipo, brapiToken);
        if(resposta == null|| resposta.getResults() == null || resposta.getResults().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Açao não encontrada " + tipo);
        }

        CotacaoAcaoResponse cotacaoAcaoResponse = resposta.getResults().get(0);
        if(cotacaoAcaoResponse.getRegularMarketPrice() == null){
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Cotacao indisponivel para " + tipo);
        }
        return cotacaoAcaoResponse;

    }
}
