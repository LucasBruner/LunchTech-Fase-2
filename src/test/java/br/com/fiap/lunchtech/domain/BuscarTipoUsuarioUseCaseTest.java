package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;
import br.com.fiap.lunchtech.core.usecases.tipousuario.BuscarTipoUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarTipoUsuarioUseCaseTest {

    private BuscarTipoUsuarioUseCase buscarTipoUsuarioUseCase;

    @Mock
    private ITipoUsuarioGateway tipoUsuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        buscarTipoUsuarioUseCase = BuscarTipoUsuarioUseCase.create(tipoUsuarioGateway);
    }

    @Test
    void deveRetornarTipoUsuarioQuandoEncontrado() {
        // Arrange
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("TIPO_EXISTENTE");
        TipoUsuario tipoUsuario = TipoUsuario.create("TIPO_EXISTENTE");
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome("TIPO_EXISTENTE")).thenReturn(tipoUsuario);

        // Act
        TipoUsuario result = buscarTipoUsuarioUseCase.run(tipoUsuarioDTO);

        // Assert
        assertNotNull(result);
        assertEquals("TIPO_EXISTENTE", result.getTipoUsuario());
        verify(tipoUsuarioGateway, times(1)).buscarTipoUsuarioPorNome("TIPO_EXISTENTE");
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioNaoEncontrado() {
        // Arrange
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("TIPO_INEXISTENTE");
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome("TIPO_INEXISTENTE")).thenReturn(null);

        // Act & Assert
        assertThrows(TipoUsuarioNaoExisteException.class, () -> buscarTipoUsuarioUseCase.run(tipoUsuarioDTO));
        verify(tipoUsuarioGateway, times(1)).buscarTipoUsuarioPorNome("TIPO_INEXISTENTE");
    }
}
