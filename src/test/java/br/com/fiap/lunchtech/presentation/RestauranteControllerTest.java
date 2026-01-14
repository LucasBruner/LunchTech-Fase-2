package br.com.fiap.lunchtech.presentation;

import br.com.fiap.lunchtech.core.controllers.RestauranteController;
import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestauranteControllerTest {

    private RestauranteController restauranteController;

    @Mock
    private IUsuarioDataSource usuarioDataSource;
    @Mock
    private IRestauranteDataSource restauranteDataSource;
    @Mock
    private ITipoUsuarioDataSource tipoUsuarioDataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restauranteController = RestauranteController.create(restauranteDataSource, usuarioDataSource, tipoUsuarioDataSource);
    }

    @Test
    void deveCadastrarRestaurante() {
        // Arrange
        NovoRestauranteDTO novoRestauranteDTO = new NovoRestauranteDTO("nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome"));
        when(usuarioDataSource.obterUsuarioPorLogin(anyString())).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "DONO_RESTAURANTE", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        when(restauranteDataSource.incluirNovoRestaurante(any(NovoRestauranteDTO.class))).thenReturn(new RestauranteDTO(1L, "nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome")));

        // Act
        RestauranteDTO result = restauranteController.cadastrarRestaurante(novoRestauranteDTO);

        // Assert
        assertNotNull(result);
        assertEquals("nome", result.nomeRestaurante());
    }

    @Test
    void deveBuscarRestaurantePorNome() {
        // Arrange
        when(restauranteDataSource.buscarRestaurantePorNome("nome")).thenReturn(new RestauranteDTO(1L, "nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome")));
        when(usuarioDataSource.obterUsuarioPorLogin(anyString())).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "DONO_RESTAURANTE", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));

        // Act
        RestauranteDTO result = restauranteController.buscarRestaurantePorNome("nome");

        // Assert
        assertNotNull(result);
        assertEquals("nome", result.nomeRestaurante());
    }

    @Test
    void deveAlterarRestaurante() {
        // Arrange
        RestauranteAlteracaoDTO restauranteAlteracaoDTO = new RestauranteAlteracaoDTO(1L, "nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome"));
        when(restauranteDataSource.buscarRestaurantePorId(anyLong())).thenReturn(new RestauranteDTO(1L, "nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome")));
        when(usuarioDataSource.obterUsuarioPorLogin(anyString())).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "DONO_RESTAURANTE", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        when(restauranteDataSource.alterarRestaurante(any(RestauranteAlteracaoDTO.class))).thenReturn(new RestauranteDTO(1L, "nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome")));

        // Act
        RestauranteDTO result = restauranteController.alterarRestaurante(restauranteAlteracaoDTO);

        // Assert
        assertNotNull(result);
        assertEquals("nome", result.nomeRestaurante());
    }

    @Test
    void deveDeletarRestaurante() {
        // Arrange
        when(restauranteDataSource.buscarRestaurantePorNome("nome")).thenReturn(new RestauranteDTO(1L, "nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome")));
        when(usuarioDataSource.obterUsuarioPorLogin(anyString())).thenReturn(new UsuarioDTO("nome",
                "email@test.com",
                "login",
                "DONO_RESTAURANTE",
                new EnderecoDTO("logradouro",
                        1,
                        "bairro",
                        "cidade",
                        "estado",
                        "01001000")));
        doNothing().when(restauranteDataSource).deletarRestaurante("nome");

        // Act
        restauranteController.deletarRestaurante("nome");

        // Assert
        verify(restauranteDataSource, times(1)).deletarRestaurante("nome");
    }
}
