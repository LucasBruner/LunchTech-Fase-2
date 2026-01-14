package br.com.fiap.lunchtech.core.usecases.tipousuario;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioJaExisteException;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarTipoUsuarioUseCaseTest {

    private CadastrarTipoUsuarioUseCase cadastrarTipoUsuarioUseCase;

    @Mock
    private ITipoUsuarioGateway tipoUsuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cadastrarTipoUsuarioUseCase = CadastrarTipoUsuarioUseCase.create(tipoUsuarioGateway);
    }

    @Test
    void deveCadastrarTipoUsuarioQuandoNaoExistir() throws TipoUsuarioNaoExisteException {
        // Arrange
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("NOVO_TIPO");
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome("NOVO_TIPO")).thenThrow(new TipoUsuarioNaoExisteException(""));
        when(tipoUsuarioGateway.incluir(any(TipoUsuario.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        TipoUsuario tipoUsuario = cadastrarTipoUsuarioUseCase.run(tipoUsuarioDTO);

        // Assert
        assertNotNull(tipoUsuario);
        assertEquals("NOVO_TIPO", tipoUsuario.getTipoUsuario());
        verify(tipoUsuarioGateway, times(1)).buscarTipoUsuarioPorNome("NOVO_TIPO");
        verify(tipoUsuarioGateway, times(1)).incluir(any(TipoUsuario.class));
    }

    @Test
    void deveLancarExcecaoQuandoTipoUsuarioJaExistir() throws TipoUsuarioNaoExisteException {
        // Arrange
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("TIPO_EXISTENTE");
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome("TIPO_EXISTENTE")).thenReturn(TipoUsuario.create("TIPO_EXISTENTE"));

        // Act & Assert
        assertThrows(TipoUsuarioJaExisteException.class, () -> cadastrarTipoUsuarioUseCase.run(tipoUsuarioDTO));
        verify(tipoUsuarioGateway, times(1)).buscarTipoUsuarioPorNome("TIPO_EXISTENTE");
        verify(tipoUsuarioGateway, never()).incluir(any(TipoUsuario.class));
    }
}
