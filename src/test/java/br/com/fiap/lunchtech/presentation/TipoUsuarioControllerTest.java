package br.com.fiap.lunchtech.presentation;

import br.com.fiap.lunchtech.core.controllers.TipoUsuarioController;
import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.gateway.TipoUsuarioGateway;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.core.usecases.tipousuario.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TipoUsuarioControllerTest {

    private TipoUsuarioController tipoUsuarioController;

    @Mock
    private ITipoUsuarioDataSource tipoUsuarioDataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tipoUsuarioController = TipoUsuarioController.create(tipoUsuarioDataSource);
    }

    @Test
    void deveCadastrarTipoUsuario() {
        // Arrange
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("NOVO_TIPO");
        when(tipoUsuarioDataSource.buscarTipoUsuarioPorNome("NOVO_TIPO")).thenReturn(null);
        when(tipoUsuarioDataSource.incluirNovoTipoUsuario(any(TipoUsuarioDTO.class))).thenReturn(tipoUsuarioDTO);

        // Act
        TipoUsuarioDTO result = tipoUsuarioController.cadastrarTipoUsuario(tipoUsuarioDTO);

        // Assert
        assertNotNull(result);
        assertEquals("NOVO_TIPO", result.tipoUsuario());
    }

    @Test
    void deveAlterarTipoUsuario() {
        // Arrange
        TipoUsuarioAlteracaoDTO tipoUsuarioAlteracaoDTO = new TipoUsuarioAlteracaoDTO("TIPO_ANTIGO", "NOVO_TIPO");
        when(tipoUsuarioDataSource.buscarTipoUsuarioPorNome("TIPO_ANTIGO")).thenReturn(new TipoUsuarioDTO("TIPO_ANTIGO"));
        when(tipoUsuarioDataSource.buscarTipoUsuarioPorNome("NOVO_TIPO")).thenReturn(null);
        when(tipoUsuarioDataSource.alterarTipoUsuario(any(TipoUsuarioDTO.class), eq("TIPO_ANTIGO"))).thenReturn(new TipoUsuarioDTO("NOVO_TIPO"));

        // Act
        TipoUsuarioDTO result = tipoUsuarioController.alterarTipoUsuario(tipoUsuarioAlteracaoDTO);

        // Assert
        assertNotNull(result);
        assertEquals("NOVO_TIPO", result.tipoUsuario());
    }

    @Test
    void deveDeletarTipoUsuario() {
        // Arrange
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("TIPO_EXISTENTE");
        when(tipoUsuarioDataSource.buscarTipoUsuarioPorNome("TIPO_EXISTENTE")).thenReturn(new TipoUsuarioDTO("TIPO_EXISTENTE"));
        doNothing().when(tipoUsuarioDataSource).deletarTipoUsuario("TIPO_EXISTENTE");

        // Act
        tipoUsuarioController.deletarTipoUsuario(tipoUsuarioDTO);

        // Assert
        verify(tipoUsuarioDataSource, times(1)).deletarTipoUsuario("TIPO_EXISTENTE");
    }

    @Test
    void deveBuscarTipoUsuarioPorNome() {
        // Arrange
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("TIPO_EXISTENTE");
        when(tipoUsuarioDataSource.buscarTipoUsuarioPorNome("TIPO_EXISTENTE")).thenReturn(new TipoUsuarioDTO("TIPO_EXISTENTE"));

        // Act
        List<TipoUsuarioDTO> result = tipoUsuarioController.buscarTipoUsuario(tipoUsuarioDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("TIPO_EXISTENTE", result.get(0).tipoUsuario());
    }

    @Test
    void deveBuscarTodosOsTiposUsuario() {
        // Arrange
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO(null);
        when(tipoUsuarioDataSource.buscarTodosTipoUsuario()).thenReturn(Arrays.asList(new TipoUsuarioDTO("TIPO1"), new TipoUsuarioDTO("TIPO2")));

        // Act
        List<TipoUsuarioDTO> result = tipoUsuarioController.buscarTipoUsuario(tipoUsuarioDTO);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
