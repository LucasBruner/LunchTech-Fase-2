package br.com.fiap.lunchtech.core.gateway;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestauranteGatewayTest {

    private RestauranteGateway restauranteGateway;

    @Mock
    private IRestauranteDataSource dataSource;
    @Mock
    private UsuarioGateway usuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restauranteGateway = RestauranteGateway.create(dataSource, usuarioGateway);
    }

    @Test
    void deveBuscarPorNome() {
        // Arrange
        when(dataSource.buscarRestaurantePorNome("nome")).thenReturn(new RestauranteDTO(1L, "nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome")));

        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getLogin()).thenReturn("login");
        when(usuarioMock.getNome()).thenReturn("Nome Dono Mock");
        when(usuarioMock.getTipoDeUsuario()).thenReturn(TipoUsuario.create("DONO_RESTAURANTE"));

        when(usuarioGateway.buscarPorLogin("login")).thenReturn(usuarioMock);

        // Act
        Restaurante result = restauranteGateway.buscarPorNome("nome");

        // Assert
        assertNotNull(result);
        assertNotNull(result.getDonoRestaurante());
        assertEquals("login", result.getDonoRestaurante().getLogin());
        assertEquals("Nome Dono Mock", result.getDonoRestaurante().getNome());
    }

    @Test
    void deveLancarExcecaoSeRestauranteNaoEncontrado() {
        when(dataSource.buscarRestaurantePorNome("invalido")).thenThrow(new RestauranteNaoEncontradoException("O restaurante desejado não foi encontrado."));
        assertThrows(RestauranteNaoEncontradoException.class, () -> restauranteGateway.buscarPorNome("invalido"));
    }

    @Test
    void deveIncluirRestaurante() {
        // Arrange
        Restaurante restaurante = mock(Restaurante.class);
        Usuario dono = mock(Usuario.class);
        when(dono.getLogin()).thenReturn("login");
        when(restaurante.getEndereco()).thenReturn(mock(Endereco.class));
        when(restaurante.getDonoRestaurante()).thenReturn(dono);
        when(dataSource.incluirNovoRestaurante(any())).thenReturn(new RestauranteDTO(1L, "nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome")));

        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getLogin()).thenReturn("login");
        when(usuarioMock.getNome()).thenReturn("Nome Dono Mock");
        when(usuarioMock.getTipoDeUsuario()).thenReturn(TipoUsuario.create("DONO_RESTAURANTE"));

        when(usuarioGateway.buscarPorLogin("login")).thenReturn(usuarioMock);

        // Act
        Restaurante result = restauranteGateway.incluir(restaurante);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getDonoRestaurante());
        assertEquals("login", result.getDonoRestaurante().getLogin());
        assertEquals("Nome Dono Mock", result.getDonoRestaurante().getNome());
        verify(dataSource, times(1)).incluirNovoRestaurante(any());
    }

    @Test
    void deveAlterarRestaurante() {
        Restaurante restaurante = mock(Restaurante.class);
        Usuario dono = mock(Usuario.class);
        when(dono.getLogin()).thenReturn("login");
        when(restaurante.getEndereco()).thenReturn(mock(Endereco.class));
        when(restaurante.getDonoRestaurante()).thenReturn(dono);
        when(dataSource.alterarRestaurante(any())).thenReturn(new RestauranteDTO(1L, "nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome")));

        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getLogin()).thenReturn("login");
        when(usuarioMock.getNome()).thenReturn("Nome Dono");
        when(usuarioMock.getTipoDeUsuario()).thenReturn(TipoUsuario.create("DONO_RESTAURANTE"));

        when(usuarioGateway.buscarPorLogin("login")).thenReturn(usuarioMock);
        Restaurante result = restauranteGateway.alterar(restaurante);
        assertNotNull(result);
        verify(dataSource, times(1)).alterarRestaurante(any());
    }

    @Test
    void deveDeletarRestaurante() {
        doNothing().when(dataSource).deletarRestaurante("nome");
        restauranteGateway.deletar("nome");
        verify(dataSource, times(1)).deletarRestaurante("nome");
    }
}
