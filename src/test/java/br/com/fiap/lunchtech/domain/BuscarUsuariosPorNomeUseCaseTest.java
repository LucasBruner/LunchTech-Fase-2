package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;
import br.com.fiap.lunchtech.core.usecases.usuario.BuscarUsuariosPorNomeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarUsuariosPorNomeUseCaseTest {

    private BuscarUsuariosPorNomeUseCase buscarUsuariosPorNomeUseCase;

    @Mock
    private IUsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        buscarUsuariosPorNomeUseCase = BuscarUsuariosPorNomeUseCase.create(usuarioGateway);
    }

    @Test
    void deveRetornarListaDeUsuariosQuandoEncontrados() {
        // Arrange
        String nome = "teste";
        List<Usuario> usuarios = Arrays.asList(mock(Usuario.class), mock(Usuario.class));
        when(usuarioGateway.buscarPorNome(nome)).thenReturn(usuarios);

        // Act
        List<Usuario> result = buscarUsuariosPorNomeUseCase.run(nome);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(usuarioGateway, times(1)).buscarPorNome(nome);
    }

    @Test
    void deveLancarExcecaoQuandoNenhumUsuarioEncontrado() {
        // Arrange
        String nome = "invalido";
        when(usuarioGateway.buscarPorNome(nome)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(UsuarioNaoEncontradoException.class, () -> buscarUsuariosPorNomeUseCase.run(nome));
        verify(usuarioGateway, times(1)).buscarPorNome(nome);
    }
}
