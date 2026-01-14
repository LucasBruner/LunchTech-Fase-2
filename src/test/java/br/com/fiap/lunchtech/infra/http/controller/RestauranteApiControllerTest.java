package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.RestauranteController;
import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class RestauranteApiControllerTest {

    private RestauranteApiController restauranteApiController;

    @Mock
    private IRestauranteDataSource restauranteDataSource;

    @Mock
    private IUsuarioDataSource usuarioDataSource;

    @Mock
    private ITipoUsuarioDataSource tipoUsuarioDataSource;

    @Mock
    private RestauranteController restauranteController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        restauranteApiController = new RestauranteApiController(restauranteDataSource, usuarioDataSource, tipoUsuarioDataSource);
        Field controllerField = RestauranteApiController.class.getDeclaredField("restauranteController");
        controllerField.setAccessible(true);
        controllerField.set(restauranteApiController, restauranteController);
    }

    @Test
    void buscarPorNome() {
        EnderecoDTO enderecoDTO = new EnderecoDTO("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        UsuarioDonoRestauranteDTO usuarioDonoRestauranteDTO = new UsuarioDonoRestauranteDTO("dono", "Dono");
        RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "Cantina da Nona", "Italiana", new Date(), new Date(), enderecoDTO, usuarioDonoRestauranteDTO);
        when(restauranteController.buscarRestaurantePorNome("Cantina da Nona")).thenReturn(restauranteDTO);

        ResponseEntity<RestauranteDTO> response = restauranteApiController.buscarPorNome("Cantina da Nona");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Cantina da Nona", response.getBody().nomeRestaurante());
    }

    @Test
    void criarRestaurante() {
        EnderecoDTO enderecoDTO = new EnderecoDTO("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        UsuarioDonoRestauranteDTO usuarioDonoRestauranteDTO = new UsuarioDonoRestauranteDTO("dono", "Dono");
        NovoRestauranteDTO novoRestauranteDTO = new NovoRestauranteDTO("Cantina da Nona", "Italiana", new Date(), new Date(), enderecoDTO, usuarioDonoRestauranteDTO);
        RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "Cantina da Nona", "Italiana", new Date(), new Date(), enderecoDTO, usuarioDonoRestauranteDTO);
        when(restauranteController.cadastrarRestaurante(novoRestauranteDTO)).thenReturn(restauranteDTO);

        ResponseEntity<Void> response = restauranteApiController.criarRestaurante(novoRestauranteDTO);

        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void alterarRestaurante() {
        EnderecoDTO enderecoDTO = new EnderecoDTO("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        UsuarioDonoRestauranteDTO usuarioDonoRestauranteDTO = new UsuarioDonoRestauranteDTO("dono", "Dono");
        RestauranteAlteracaoDTO restauranteAlteracaoDTO = new RestauranteAlteracaoDTO(1L, "Cantina da Nona", "Italiana", new Date(), new Date(), enderecoDTO, usuarioDonoRestauranteDTO);
        RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "Cantina da Nona", "Italiana", new Date(), new Date(), enderecoDTO, usuarioDonoRestauranteDTO);
        when(restauranteController.alterarRestaurante(restauranteAlteracaoDTO)).thenReturn(restauranteDTO);

        ResponseEntity<Void> response = restauranteApiController.alterarRestaurante(restauranteAlteracaoDTO);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void deletarRestaurante() {
        doNothing().when(restauranteController).deletarRestaurante("Cantina da Nona");

        ResponseEntity<Void> response = restauranteApiController.deletarRestaurante("Cantina da Nona");

        assertEquals(200, response.getStatusCode().value());
    }
}