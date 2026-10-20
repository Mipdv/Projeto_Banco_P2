package com.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

// Devolve os erros como {"message": "..."} com o status que o front espera.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Map<String, String>> conflito(ConflictException e) {
        return resposta(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> dadoDuplicado(DataIntegrityViolationException e) {
        return resposta(HttpStatus.CONFLICT, "Já existe um cadastro com estes dados");
    }

    @ExceptionHandler(ResoucerNotFoundException.class)
    public ResponseEntity<Map<String, String>> naoEncontrado(ResoucerNotFoundException e) {
        return resposta(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> requisicaoInvalida(IllegalArgumentException e) {
        return resposta(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> statusDefinido(ResponseStatusException e) {
        return resposta(HttpStatus.valueOf(e.getStatusCode().value()), e.getReason());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> naoAutenticado(AuthenticationException e) {
        return resposta(HttpStatus.UNAUTHORIZED, "E-mail ou senha incorretos");
    }

    private ResponseEntity<Map<String, String>> resposta(HttpStatus status, String mensagem) {
        return ResponseEntity.status(status).body(Map.of("message", mensagem == null ? status.getReasonPhrase() : mensagem));
    }
}
