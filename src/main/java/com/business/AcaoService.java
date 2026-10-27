package com.business;


import com.exceptions.ResoucerNotFoundException;
import com.infrastructure.Client.AcaoClient;
import com.infrastructure.entity.BrapiResponse;
import com.infrastructure.entity.Conta;
import com.infrastructure.entity.CotacaoAcaoResponse;
import com.infrastructure.security.JwtUtil;
import com.repository.ContaRepository;
import com.repository.NegociacaoRepository;
import com.repository.PosicaoCarteira;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AcaoService {
    private final JwtUtil jwtUtil;
    private final AcaoClient acaoClient;
    private final NegociacaoRepository negociacaoRepository;
    private final ContaRepository contaRepository;
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

    public Map<String, Object> carteira(String token){
        Conta conta = buscaContaDoToken(token);

        List<Map<String, Object>> acoes = new ArrayList<>();
        BigDecimal totalAcoes = BigDecimal.ZERO;

        for (PosicaoCarteira posicao : negociacaoRepository.buscarCarteira(conta)) {
            BigDecimal precoAtual = buscar(posicao.getAcao()).getRegularMarketPrice();
            BigDecimal total = precoAtual.multiply(BigDecimal.valueOf(posicao.getQuantidade()))
                    .setScale(2, RoundingMode.HALF_EVEN);
            totalAcoes = totalAcoes.add(total);

            Map<String, Object> linha = new LinkedHashMap<>();
            linha.put("acao", posicao.getAcao());
            linha.put("quantidade", posicao.getQuantidade());
            linha.put("precoAtual", precoAtual);
            linha.put("total", total);
            acoes.add(linha);
        }

        Map<String, Object> resposta = new LinkedHashMap<>();
        resposta.put("acoes", acoes);
        resposta.put("saldo", conta.getSaldo());
        resposta.put("patrimonio", conta.getSaldo().add(totalAcoes));
        return resposta;
    }

    private Conta buscaContaDoToken(String token){
        String email = jwtUtil.extractUsername(token.substring(7));
        return contaRepository.findByUsuarioEmail(email).orElseThrow(() ->
                new ResoucerNotFoundException("Conta não encontrada"));
    }
}
