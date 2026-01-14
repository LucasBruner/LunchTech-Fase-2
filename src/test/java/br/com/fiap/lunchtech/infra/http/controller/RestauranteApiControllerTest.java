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
import br.com.fiap.lunchtech.infra.http.controller.json.EnderecoJson;
import br.com.fiap.lunchtech.infra.http.controller.json.RestauranteJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
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
        RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "Cantina da Nona", "Italiana", LocalTime.now(), LocalTime.now(), enderecoDTO, usuarioDonoRestauranteDTO);
        when(restauranteController.buscarRestaurantePorNome("Cantina da Nona")).thenReturn(restauranteDTO);

        ResponseEntity<RestauranteDTO> response = restauranteApiController.buscarPorNome("Cantina da Nona");

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Cantina da Nona", response.getBody().nomeRestaurante());
    }

    @Test
    void criarRestaurante() {
        // Given
        EnderecoJson enderecoJson = new EnderecoJson("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        RestauranteJson restauranteJson = new RestauranteJson();
        setField(restauranteJson, "nomeRestaurante", "Cantina da Nona");
        setField(restauranteJson, "tipoCozinha", "Italiana");
        setField(restauranteJson, "horarioFuncionamentoInicio", LocalTime.of(10, 0));
        setField(restauranteJson, "horarioFuncionamentoFim", LocalTime.of(22, 0));
        setField(restauranteJson, "endereco", enderecoJson);
        setField(restauranteJson, "login", "dono");

        EnderecoDTO enderecoDTO = new EnderecoDTO("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        UsuarioDonoRestauranteDTO usuarioDonoRestauranteDTO = new UsuarioDonoRestauranteDTO("dono", "Dono");
        RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "Cantina da Nona", "Italiana", LocalTime.now(), LocalTime.now(), enderecoDTO, usuarioDonoRestauranteDTO);

        when(restauranteController.cadastrarRestaurante(any(NovoRestauranteDTO.class))).thenReturn(restauranteDTO);

        // When
        ResponseEntity<Void> response = restauranteApiController.criarRestaurante(restauranteJson);

        // Then
        assertEquals(201, response.getStatusCode().value());
        
        ArgumentCaptor<NovoRestauranteDTO> captor = ArgumentCaptor.forClass(NovoRestauranteDTO.class);
        verify(restauranteController).cadastrarRestaurante(captor.capture());

        NovoRestauranteDTO capturedDto = captor.getValue();
        assertEquals("Cantina da Nona", capturedDto.nomeRestaurante());
        assertEquals("dono", capturedDto.donoRestaurante().login());
    }


    @Test
    void alterarRestaurante() {
        // Given
        EnderecoJson enderecoJson = new EnderecoJson("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        RestauranteJson restauranteJson = new RestauranteJson();
        setField(restauranteJson, "nomeRestaurante", "Cantina da Nona");
        setField(restauranteJson, "tipoCozinha", "Italiana");
        setField(restauranteJson, "horarioFuncionamentoInicio", LocalTime.of(10, 0));
        setField(restauranteJson, "horarioFuncionamentoFim", LocalTime.of(22, 0));
        setField(restauranteJson, "endereco", enderecoJson);
        setField(restauranteJson, "login", "dono");


        EnderecoDTO enderecoDTO = new EnderecoDTO("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        UsuarioDonoRestauranteDTO usuarioDonoRestauranteDTO = new UsuarioDonoRestauranteDTO("dono", "Dono");
        RestauranteAlteracaoDTO restauranteAlteracaoDTO = new RestauranteAlteracaoDTO(1L, "Cantina da Nona", "Italiana", LocalTime.now(), LocalTime.now(), enderecoDTO, usuarioDonoRestauranteDTO);
        RestauranteDTO restauranteDTO = new RestauranteDTO(1L, "Cantina da Nona", "Italiana", LocalTime.now(), LocalTime.now(), enderecoDTO, usuarioDonoRestauranteDTO);

        when(restauranteController.alterarRestaurante(restauranteAlteracaoDTO)).thenReturn(restauranteDTO);

        ResponseEntity<Void> response = restauranteApiController.alterarRestaurante(restauranteJson);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void deletarRestaurante() {
        doNothing().when(restauranteController).deletarRestaurante("Cantina da Nona");

        ResponseEntity<Void> response = restauranteApiController.deletarRestaurante("Cantina da Nona");

        assertEquals(200, response.getStatusCode().value());
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}