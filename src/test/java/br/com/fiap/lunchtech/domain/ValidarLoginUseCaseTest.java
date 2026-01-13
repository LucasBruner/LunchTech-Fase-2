package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.LoginInvalidoException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;
import br.com.fiap.lunchtech.core.usecases.usuario.ValidarLoginUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidarLoginUseCaseTest {

    private ValidarLoginUseCase validarLoginUseCase;

    @Mock
    private IUsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validarLoginUseCase = ValidarLoginUseCase.create(usuarioGateway);
    }

    @Test
    void deveRetornarTrueQuandoLoginValido() {
        // Arrange
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO("login", "senha");
        Usuario usuario = mock(Usuario.class);
        when(usuario.getLogin()).thenReturn("login");
        when(usuario.getSenha()).thenReturn("senha");
        when(usuarioGateway.buscarDadosLogin("login")).thenReturn(usuario);

        // Act
        boolean result = validarLoginUseCase.run(usuarioSenhaDTO);

        // Assert
        assertTrue(result);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO("login_invalido", "senha");
        when(usuarioGateway.buscarDadosLogin("login_invalido")).thenReturn(null);

        // Act & Assert
        assertThrows(LoginInvalidoException.class, () -> validarLoginUseCase.run(usuarioSenhaDTO));
    }

    @Test
    void deveLancarExcecaoQuandoSenhaIncorreta() {
        // Arrange
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO("login", "senha_incorreta");
        Usuario usuario = mock(Usuario.class);
        when(usuario.getLogin()).thenReturn("login");
        when(usuario.getSenha()).thenReturn("senha_correta");
        when(usuarioGateway.buscarDadosLogin("login")).thenReturn(usuario);

        // Act & Assert
        assertThrows(LoginInvalidoException.class, () -> validarLoginUseCase.run(usuarioSenhaDTO));
    }
}
