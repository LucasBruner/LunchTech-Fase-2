package br.com.fiap.lunchtech.infra.http.handlers;

import br.com.fiap.lunchtech.core.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerExceptionHandlerTest {

    private ControllerExceptionHandler controllerExceptionHandler;

    @BeforeEach
    void setUp() {
        controllerExceptionHandler = new ControllerExceptionHandler();
    }

    @Test
    void handlerRestauranteNaoEncontradoException() {
        RestauranteNaoEncontradoException e = new RestauranteNaoEncontradoException("Restaurante não encontrado");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerRestauranteNaoEncontradoException(e);
        assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.getStatus());
        assertEquals("Restaurante não encontrado!", problemDetail.getTitle());
    }

    @Test
    void handlerRestauranteJaExistenteException() {
        RestauranteJaExistenteException e = new RestauranteJaExistenteException("Restaurante já existente");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerRestauranteJaExistenteException(e);
        assertEquals(HttpStatus.CONFLICT.value(), problemDetail.getStatus());
        assertEquals("Restaurante já cadastrado!", problemDetail.getTitle());
    }

    @Test
    void handlerUsuarioNaoEncontradoException() {
        UsuarioNaoEncontradoException e = new UsuarioNaoEncontradoException("Usuário não encontrado");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerUsuarioNaoEncontradoException(e);
        assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.getStatus());
        assertEquals("Usuário não encontrado!", problemDetail.getTitle());
    }

    @Test
    void handlerTipoUsuarioNaoExisteException() {
        TipoUsuarioNaoExisteException e = new TipoUsuarioNaoExisteException("Tipo de usuário não existe");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerTipoUsuarioNaoExisteException(e);
        assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.getStatus());
        assertEquals("Tipo de usuário não encontrado!", problemDetail.getTitle());
    }

    @Test
    void handlerTipoUsuarioJaExisteException() {
        TipoUsuarioJaExisteException e = new TipoUsuarioJaExisteException("Tipo de usuário já existe");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerTipoUsuarioJaExisteException(e);
        assertEquals(HttpStatus.CONFLICT.value(), problemDetail.getStatus());
        assertEquals("Tipo de usuário já existe!", problemDetail.getTitle());
    }

    @Test
    void handlerUsuarioJaExisteException() {
        UsuarioJaExisteException e = new UsuarioJaExisteException("Usuário já existe");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerUsuarioJaExisteException(e);
        assertEquals(HttpStatus.CONFLICT.value(), problemDetail.getStatus());
        assertEquals("Tipo de usuário já existe!", problemDetail.getTitle());
    }

    @Test
    void handlerUsuarioComInformacaoInvalidaException() {
        UsuarioComInformacaoInvalidaException e = new UsuarioComInformacaoInvalidaException("Informação inválida");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerUsuarioComInformacaoInvalidaException(e);
        assertEquals(HttpStatus.BAD_REQUEST.value(), problemDetail.getStatus());
        assertEquals("Tipo de usuário inválido!", problemDetail.getTitle());
    }

    @Test
    void handlerUsuarioComEmailJaCadastradoException() {
        UsuarioComEmailJaCadastradoException e = new UsuarioComEmailJaCadastradoException("Email já cadastrado");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerUsuarioComEmailJaCadastradoException(e);
        assertEquals(HttpStatus.CONFLICT.value(), problemDetail.getStatus());
        assertEquals("Email já cadastrado!", problemDetail.getTitle());
    }

    @Test
    void handlerCardapioNaoExisteException() {
        CardapioNaoExisteException e = new CardapioNaoExisteException("Cardápio não existe");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerCardapioNaoExisteException(e);
        assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.getStatus());
        assertEquals("Produto não encontrado!", problemDetail.getTitle());
    }

    @Test
    void handlerCardapioJaExisteException() {
        CardapioJaExisteException e = new CardapioJaExisteException("Cardápio já existe");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerCardapioJaExisteException(e);
        assertEquals(HttpStatus.CONFLICT.value(), problemDetail.getStatus());
        assertEquals("Item do cardápio já existe!", problemDetail.getTitle());
    }

    @Test
    void handlerIllegalArgumentException() {
        IllegalArgumentException e = new IllegalArgumentException("Argumento ilegal");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerIllegalArgumentException(e);
        assertEquals(HttpStatus.BAD_REQUEST.value(), problemDetail.getStatus());
        assertEquals("Informação inválida!", problemDetail.getTitle());
    }

    @Test
    void handlerTipoDeUsuarioExisteException() {
        TipoDeUsuarioExisteException e = new TipoDeUsuarioExisteException("Tipo de usuário existe");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerTipoDeUsuarioExisteException(e);
        assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.getStatus());
        assertEquals("Tipo de usuário não encontrado!", problemDetail.getTitle());
    }

    @Test
    void handlerTipoDeUsuarioNaoPodeSerExcluidoException() {
        TipoDeUsuarioNaoPodeSerExcluidoException e = new TipoDeUsuarioNaoPodeSerExcluidoException("Tipo de usuário não pode ser excluído");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerTipoDeUsuarioNaoPodeSerExcluidoException(e);
        assertEquals(HttpStatus.NOT_FOUND.value(), problemDetail.getStatus());
        assertEquals("Tipo de usuário não pode ser excluído!", problemDetail.getTitle());
    }

    @Test
    void handlerLoginInvalidoException() {
        LoginInvalidoException e = new LoginInvalidoException("Login inválido!");
        ProblemDetail problemDetail = controllerExceptionHandler.handlerLoginInvalidoException(e);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), problemDetail.getStatus());
        assertEquals("Login inválido!", problemDetail.getTitle());
    }
}
