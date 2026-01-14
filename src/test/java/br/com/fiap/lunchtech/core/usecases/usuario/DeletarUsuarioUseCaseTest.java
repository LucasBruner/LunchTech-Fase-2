package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeletarUsuarioUseCaseTest {

    private DeletarUsuarioUseCase deletarUsuarioUseCase;

    @Mock
    private IUsuarioGateway usuarioGateway;
    @Mock
    private IRestauranteGateway restauranteGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deletarUsuarioUseCase = DeletarUsuarioUseCase.create(usuarioGateway, restauranteGateway);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
        // Arrange
        String login = "invalido";
        when(usuarioGateway.buscarPorLogin(login)).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> deletarUsuarioUseCase.run(login));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioAssociadoArestaurante() {
        // Arrange
        String login = "login";
        Usuario usuario = mock(Usuario.class);
        when(usuarioGateway.buscarPorLogin(login)).thenReturn(null);
        when(restauranteGateway.buscarRestaurantesPorLogin(usuario)).thenReturn(Arrays.asList(mock(Restaurante.class)));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> deletarUsuarioUseCase.run(login));
    }

    @Test
    void deveDeletarUsuarioComSucesso() {
        // Arrange
        String login = "login";
        Usuario usuario = mock(Usuario.class);
        when(usuarioGateway.buscarPorLogin(login)).thenReturn(usuario);
        when(restauranteGateway.buscarRestaurantesPorLogin(usuario)).thenReturn(Collections.emptyList());
        doNothing().when(usuarioGateway).deletar(login);

        // Act
        deletarUsuarioUseCase.run(login);

        // Assert
        verify(usuarioGateway, times(1)).deletar(login);
    }
}
