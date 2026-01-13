package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoDeUsuarioNaoPodeSerExcluidoException;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;
import br.com.fiap.lunchtech.core.usecases.tipousuario.DeletarTipoUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeletarTipoUsuarioUseCaseTest {

    private DeletarTipoUsuarioUseCase deletarTipoUsuarioUseCase;

    @Mock
    private ITipoUsuarioGateway tipoUsuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deletarTipoUsuarioUseCase = DeletarTipoUsuarioUseCase.create(tipoUsuarioGateway);
    }

    @Test
    void deveLancarExcecaoAoDeletarTipoUsuarioCliente() {
        // Arrange
        TipoUsuarioDTO dto = new TipoUsuarioDTO("CLIENTE");

        // Act & Assert
        assertThrows(TipoDeUsuarioNaoPodeSerExcluidoException.class, () -> deletarTipoUsuarioUseCase.run(dto));
    }

    @Test
    void deveLancarExcecaoAoDeletarTipoUsuarioDonoRestaurante() {
        // Arrange
        TipoUsuarioDTO dto = new TipoUsuarioDTO("DONO_RESTAURANTE");

        // Act & Assert
        assertThrows(TipoDeUsuarioNaoPodeSerExcluidoException.class, () -> deletarTipoUsuarioUseCase.run(dto));
    }

    @Test
    void deveLancarExcecaoSeTipoUsuarioNaoExiste() {
        // Arrange
        TipoUsuarioDTO dto = new TipoUsuarioDTO("TIPO_INEXISTENTE");
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome("TIPO_INEXISTENTE")).thenReturn(null);

        // Act & Assert
        assertThrows(TipoUsuarioNaoExisteException.class, () -> deletarTipoUsuarioUseCase.run(dto));
    }

    @Test
    void deveDeletarTipoUsuarioComSucesso() {
        // Arrange
        TipoUsuarioDTO dto = new TipoUsuarioDTO("TIPO_EXISTENTE");
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome("TIPO_EXISTENTE")).thenReturn(TipoUsuario.create("TIPO_EXISTENTE"));
        doNothing().when(tipoUsuarioGateway).deletar("TIPO_EXISTENTE");

        // Act
        deletarTipoUsuarioUseCase.run(dto);

        // Assert
        verify(tipoUsuarioGateway, times(1)).deletar("TIPO_EXISTENTE");
    }
}
