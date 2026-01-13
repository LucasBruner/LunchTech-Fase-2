package br.com.fiap.lunchtech.infra.http.handlers;

import br.com.fiap.lunchtech.core.exceptions.RestauranteJaExistenteException;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.exceptions.UsuarioNaoEncontradoException;
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

    @ExceptionHandler(RestauranteJaExistenteException.class)
    public ProblemDetail handlerRestauranteJaExistenteException(RestauranteJaExistenteException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Restaurante já cadastrado!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/restaurantes"));
        return problemDetail;
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ProblemDetail handlerUsuarioNaoEncontradoException(UsuarioNaoEncontradoException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Usuário não encontrado!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/usuarios"));
        return problemDetail;
    }
}
