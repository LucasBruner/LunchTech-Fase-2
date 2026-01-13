package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;
import br.com.fiap.lunchtech.core.usecases.tipousuario.BuscarTodosTipoUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarTodosTipoUsuarioUseCaseTest {

    private BuscarTodosTipoUsuarioUseCase buscarTodosTipoUsuarioUseCase;

    @Mock
    private ITipoUsuarioGateway tipoUsuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        buscarTodosTipoUsuarioUseCase = BuscarTodosTipoUsuarioUseCase.create(tipoUsuarioGateway);
    }

    @Test
    void deveRetornarListaDeTiposUsuarioQuandoExistirem() {
        // Arrange
        List<TipoUsuario> tiposUsuario = Arrays.asList(TipoUsuario.create("TIPO1"),TipoUsuario.create("TIPO2"));
        when(tipoUsuarioGateway.buscarTodosTipoUsuario()).thenReturn(tiposUsuario);

        // Act
        List<TipoUsuario> result = buscarTodosTipoUsuarioUseCase.run();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tipoUsuarioGateway, times(1)).buscarTodosTipoUsuario();
    }

    @Test
    void deveLancarExcecaoQuandoNaoExistiremTiposUsuario() {
        // Arrange
        when(tipoUsuarioGateway.buscarTodosTipoUsuario()).thenReturn(null);

        // Act & Assert
        assertThrows(TipoUsuarioNaoExisteException.class, () -> buscarTodosTipoUsuarioUseCase.run());
        verify(tipoUsuarioGateway, times(1)).buscarTodosTipoUsuario();
    }
}
