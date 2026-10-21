package com.infrastructure.Client.configs;


import com.exceptions.ConflictException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;


public class FeignError implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        String mensagemErro = mensagemErro(response);

        return switch (response.status()) {
            case 400 -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro de requisição: " + mensagemErro);

            case 404 -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Par de moedas inválido");

            case 401, 403 -> new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Acesso ao serviço de cotação negado: " + mensagemErro);
            case 409 -> new ConflictException("Atributo já existente: " + mensagemErro);
            case 429 -> comRetry(response,
                    new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Limite de requisições excedido: " + mensagemErro));
            case 500, 502, 503, 504 -> comRetry(response,
                    new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Serviço temporariamente indisponível: " + mensagemErro));
            default -> new ErrorDecoder.Default().decode(s, response);
        };
    }

    private RetryableException comRetry(Response response, RuntimeException causa) {
        return new RetryableException(
                response.status(),
                causa.getMessage(),
                response.request().httpMethod(),
                causa,
                instanteRetryAfter(response),
                response.request());
    }

    private Long instanteRetryAfter(Response response) {
        String valor = cabecalho(response, "Retry-After");
        if (valor == null || valor.isBlank()) {
            return null;
        }
        valor = valor.trim();

        try {
            return System.currentTimeMillis() + Long.parseLong(valor) * 1000L;
        } catch (NumberFormatException naoEhSegundos) {
            try {
                return ZonedDateTime.parse(valor, DateTimeFormatter.RFC_1123_DATE_TIME)
                        .toInstant().toEpochMilli();
            } catch (DateTimeParseException naoEhData) {
                return null;
            }
        }
    }

    private String cabecalho(Response response, String nome) {
        Map<String, Collection<String>> headers = response.headers();
        if (headers == null) {
            return null;
        }
        for (Map.Entry<String, Collection<String>> entrada : headers.entrySet()) {
            if (nome.equalsIgnoreCase(entrada.getKey())
                    && entrada.getValue() != null
                    && !entrada.getValue().isEmpty()) {
                return entrada.getValue().iterator().next();
            }
        }
        return null;
    }

    private String mensagemErro(Response response) {
        try {
            if (Objects.isNull(response.body())) {
                return "";
            }
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }
}
