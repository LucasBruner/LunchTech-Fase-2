package br.com.fiap.lunchtech.infra.http.handlers;

import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(RestauranteNaoEncontradoException.class)
    public ProblemDetail handlerRestauranteNaoEncontradoException(RestauranteNaoEncontradoException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Restaurante não encontrado!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/restaurantes"));
        return problemDetail;
    }
}
