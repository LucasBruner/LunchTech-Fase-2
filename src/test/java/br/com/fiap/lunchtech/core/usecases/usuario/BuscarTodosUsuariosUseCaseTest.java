package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarTodosUsuariosUseCaseTest {

    private BuscarTodosUsuariosUseCase buscarTodosUsuariosUseCase;

    @Mock
    private IUsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        buscarTodosUsuariosUseCase = BuscarTodosUsuariosUseCase.create(usuarioGateway);
    }

    @Test
    void deveRetornarListaDeUsuariosQuandoEncontrados() {
        // Arrange
        List<Usuario> usuarios = Arrays.asList(mock(Usuario.class), mock(Usuario.class));
        when(usuarioGateway.buscarTodos()).thenReturn(usuarios);

        // Act
        List<Usuario> result = buscarTodosUsuariosUseCase.run();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(usuarioGateway, times(1)).buscarTodos();
    }

    @Test
    void deveLancarExcecaoQuandoNenhumUsuarioEncontrado() {
        // Arrange
        when(usuarioGateway.buscarTodos()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () -> buscarTodosUsuariosUseCase.run());
        verify(usuarioGateway, times(1)).buscarTodos();
    }
}
