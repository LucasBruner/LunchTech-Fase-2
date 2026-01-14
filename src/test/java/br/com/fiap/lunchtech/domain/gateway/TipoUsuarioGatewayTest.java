package br.com.fiap.lunchtech.domain.gateway;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.gateway.TipoUsuarioGateway;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipoUsuarioGatewayTest {

    private TipoUsuarioGateway tipoUsuarioGateway;

    @Mock
    private ITipoUsuarioDataSource dataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tipoUsuarioGateway = TipoUsuarioGateway.create(dataSource);
    }

    @Test
    void deveIncluirTipoUsuario() {
        TipoUsuario tipoUsuario = TipoUsuario.create("TESTE");
        when(dataSource.incluirNovoTipoUsuario(any(TipoUsuarioDTO.class))).thenReturn(new TipoUsuarioDTO("TESTE"));

        TipoUsuario result = tipoUsuarioGateway.incluir(tipoUsuario);

        assertNotNull(result);
        assertEquals("TESTE", result.getTipoUsuario());
        verify(dataSource, times(1)).incluirNovoTipoUsuario(any(TipoUsuarioDTO.class));
    }

    @Test
    void deveAlterarTipoUsuario() {
        TipoUsuario tipoUsuario = TipoUsuario.create("NOVO_TESTE");
        when(dataSource.alterarTipoUsuario(any(TipoUsuarioDTO.class), eq("ANTIGO_TESTE"))).thenReturn(new TipoUsuarioDTO("NOVO_TESTE"));

        TipoUsuario result = tipoUsuarioGateway.alterar(tipoUsuario, "ANTIGO_TESTE");

        assertNotNull(result);
        assertEquals("NOVO_TESTE", result.getTipoUsuario());
        verify(dataSource, times(1)).alterarTipoUsuario(any(TipoUsuarioDTO.class), eq("ANTIGO_TESTE"));
    }

    @Test
    void deveDeletarTipoUsuario() {
        doNothing().when(dataSource).deletarTipoUsuario("TESTE");
        tipoUsuarioGateway.deletar("TESTE");
        verify(dataSource, times(1)).deletarTipoUsuario("TESTE");
    }

    @Test
    void deveBuscarTipoUsuarioPorNome() {
        when(dataSource.buscarTipoUsuarioPorNome("TESTE")).thenReturn(new TipoUsuarioDTO("TESTE"));
        TipoUsuario result = tipoUsuarioGateway.buscarTipoUsuarioPorNome("TESTE");
        assertNotNull(result);
        assertEquals("TESTE", result.getTipoUsuario());
    }

    @Test
    void deveLancarExcecaoSeTipoUsuarioNaoEncontrado() {
        when(dataSource.buscarTipoUsuarioPorNome("INEXISTENTE")).thenReturn(null);
        assertThrows(TipoUsuarioNaoExisteException.class, () -> tipoUsuarioGateway.buscarTipoUsuarioPorNome("INEXISTENTE"));
    }

    @Test
    void deveBuscarTodosOsTiposUsuario() {
        when(dataSource.buscarTodosTipoUsuario()).thenReturn(Arrays.asList(new TipoUsuarioDTO("T1"), new TipoUsuarioDTO("T2")));
        List<TipoUsuario> result = tipoUsuarioGateway.buscarTodosTipoUsuario();
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
