package br.com.fiap.lunchtech.infra.http.handlers;

import br.com.fiap.lunchtech.core.exceptions.*;
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

    @ExceptionHandler(TipoUsuarioNaoExisteException.class)
    public ProblemDetail handlerUsuarioNaoEncontradoException(TipoUsuarioNaoExisteException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Tipo de usuário não encontrado!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/tipo-usuario"));
        return problemDetail;
    }

    @ExceptionHandler(TipoUsuarioJaExisteException.class)
    public ProblemDetail handlerUsuarioNaoEncontradoException(TipoUsuarioJaExisteException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Tipo de usuário já existe!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/tipo-usuario"));
        return problemDetail;
    }

    @ExceptionHandler(UsuarioJaExisteException.class)
    public ProblemDetail handlerUsuarioNaoEncontradoException(UsuarioJaExisteException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Tipo de usuário já existe!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/tipo-usuario"));
        return problemDetail;
    }

    @ExceptionHandler(UsuarioComInformacaoInvalidaException.class)
    public ProblemDetail handlerUsuarioNaoEncontradoException(UsuarioComInformacaoInvalidaException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Tipo de usuário inválido!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/tipo-usuario"));
        return problemDetail;
    }

    @ExceptionHandler(UsuarioComEmailJaCadastradoException.class)
    public ProblemDetail handlerUsuarioNaoEncontradoException(UsuarioComEmailJaCadastradoException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Email já cadastrado!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/usuarios"));
        return problemDetail;
    }

    @ExceptionHandler(CardapioNaoExisteException.class)
    public ProblemDetail handlerCardapioNaoExisteException(CardapioNaoExisteException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Produto não encontrado!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/cardapio"));
        return problemDetail;
    }

    @ExceptionHandler(CardapioJaExisteException.class)
    public ProblemDetail handlerCardapioJaExisteException(CardapioJaExisteException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Item do cardápio já existe!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/cardapio"));
        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handlerIllegalArgumentException(IllegalArgumentException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Informação inválida!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/cardapio"));
        return problemDetail;
    }

    @ExceptionHandler(TipoDeUsuarioExisteException.class)
    public ProblemDetail handlerTipoDeUsuarioExisteException(TipoDeUsuarioExisteException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Tipo de usuário não encontrado!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/tipo-usuario"));
        return problemDetail;
    }

    @ExceptionHandler(TipoDeUsuarioNaoPodeSerExcluidoException.class)
    public ProblemDetail handlerTipoDeUsuarioNaoPodeSerExcluidoException(TipoDeUsuarioNaoPodeSerExcluidoException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Tipo de usuário não pode ser excluído!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/tipo-usuario"));
        return problemDetail;
    }

    @ExceptionHandler(LoginInvalidoException.class)
    public ProblemDetail handlerLoginInvalidoException(LoginInvalidoException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("Login inválido!");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setType(URI.create("http://localhost:8080/v1/login"));
        return problemDetail;
    }
}
