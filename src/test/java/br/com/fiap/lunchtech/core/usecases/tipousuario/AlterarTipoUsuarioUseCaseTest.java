package br.com.fiap.lunchtech.core.usecases.tipousuario;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoDeUsuarioExisteException;
import br.com.fiap.lunchtech.core.exceptions.TipoDeUsuarioNaoPodeSerExcluidoException;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlterarTipoUsuarioUseCaseTest {

    private AlterarTipoUsuarioUseCase alterarTipoUsuarioUseCase;

    @Mock
    private ITipoUsuarioGateway tipoUsuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alterarTipoUsuarioUseCase = AlterarTipoUsuarioUseCase.create(tipoUsuarioGateway);
    }

    @Test
    void deveLancarExcecaoAoAlterarTipoUsuarioCliente() {
        // Arrange
        TipoUsuarioAlteracaoDTO dto = new TipoUsuarioAlteracaoDTO("CLIENTE", "NOVO_TIPO");

        // Act & Assert
        assertThrows(TipoDeUsuarioNaoPodeSerExcluidoException.class, () -> alterarTipoUsuarioUseCase.run(dto));
    }

    @Test
    void deveLancarExcecaoAoAlterarTipoUsuarioDonoRestaurante() {
        // Arrange
        TipoUsuarioAlteracaoDTO dto = new TipoUsuarioAlteracaoDTO("DONO_RESTAURANTE", "NOVO_TIPO");

        // Act & Assert
        assertThrows(TipoDeUsuarioNaoPodeSerExcluidoException.class, () -> alterarTipoUsuarioUseCase.run(dto));
    }

    @Test
    void deveLancarExcecaoSeNovoTipoUsuarioJaExiste() {
        // Arrange
        TipoUsuarioAlteracaoDTO dto = new TipoUsuarioAlteracaoDTO("TIPO_ANTIGO", "NOVO_TIPO_EXISTENTE");
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome("TIPO_ANTIGO")).thenReturn(TipoUsuario.create("TIPO_ANTIGO"));
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome("NOVO_TIPO_EXISTENTE")).thenReturn(TipoUsuario.create("NOVO_TIPO_EXISTENTE"));

        // Act & Assert
        assertThrows(TipoDeUsuarioExisteException.class, () -> alterarTipoUsuarioUseCase.run(dto));
    }

    @Test
    void deveLancarExcecaoSeTipoUsuarioASerAlteradoNaoExiste() {
        // Arrange
        TipoUsuarioAlteracaoDTO dto = new TipoUsuarioAlteracaoDTO("TIPO_INEXISTENTE", "NOVO_TIPO");
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome("TIPO_INEXISTENTE")).thenThrow(new TipoUsuarioNaoExisteException(""));

        // Act & Assert
        assertThrows(TipoUsuarioNaoExisteException.class, () -> alterarTipoUsuarioUseCase.run(dto));
    }

    @Test
    void deveAlterarTipoUsuarioComSucesso() {
        // Arrange
        TipoUsuarioAlteracaoDTO dto = new TipoUsuarioAlteracaoDTO("TIPO_ANTIGO", "NOVO_TIPO");
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome("TIPO_ANTIGO")).thenReturn(TipoUsuario.create("TIPO_ANTIGO"));
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome("NOVO_TIPO")).thenThrow(new TipoUsuarioNaoExisteException(""));
        when(tipoUsuarioGateway.alterar(any(TipoUsuario.class), eq("TIPO_ANTIGO"))).thenAnswer(i -> i.getArgument(0));

        // Act
        TipoUsuario result = alterarTipoUsuarioUseCase.run(dto);

        // Assert
        assertNotNull(result);
        assertEquals("NOVO_TIPO", result.getTipoUsuario());
        verify(tipoUsuarioGateway, times(1)).alterar(any(TipoUsuario.class), eq("TIPO_ANTIGO"));
    }
}
