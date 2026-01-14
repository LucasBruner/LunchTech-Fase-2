package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioJaExisteException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AlterarSenhaUseCaseTest {

    private AlterarSenhaUseCase alterarSenhaUseCase;

    @Mock
    private IUsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alterarSenhaUseCase = AlterarSenhaUseCase.create(usuarioGateway);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        // Arrange
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO("login_invalido", "nova_senha");
        when(usuarioGateway.buscarPorLogin("login_invalido")).thenReturn(null);

        // Act & Assert
        assertThrows(UsuarioJaExisteException.class, () -> alterarSenhaUseCase.run(usuarioSenhaDTO));
    }

    @Test
    void deveAlterarSenhaComSucesso() {
        // Arrange
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO("login", "nova_senha");
        when(usuarioGateway.buscarPorLogin("login")).thenReturn(mock(Usuario.class));
        when(usuarioGateway.alterarSenha(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Usuario result = alterarSenhaUseCase.run(usuarioSenhaDTO);

        // Assert
        assertNotNull(result);
        assertEquals("nova_senha", result.getSenha());
        verify(usuarioGateway, times(1)).alterarSenha(any(Usuario.class));
    }
}
