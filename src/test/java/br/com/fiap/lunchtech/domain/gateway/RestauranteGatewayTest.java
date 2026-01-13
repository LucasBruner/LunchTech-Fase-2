package br.com.fiap.lunchtech.domain.gateway;

import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.gateway.RestauranteGateway;
import br.com.fiap.lunchtech.core.gateway.UsuarioGateway;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        when(dataSource.buscarRestaurantePorNome("nome")).thenReturn(mock(RestauranteDTO.class));
        Restaurante result = restauranteGateway.buscarPorNome("nome");
        assertNotNull(result);
    }

    @Test
    void deveRetornarNuloSeRestauranteNaoEncontrado() {
        when(dataSource.buscarRestaurantePorNome("invalido")).thenReturn(null);
        Restaurante result = restauranteGateway.buscarPorNome("invalido");
        assertNull(result);
    }

    @Test
    void deveIncluirRestaurante() {
        Restaurante restaurante = mock(Restaurante.class);
        when(dataSource.incluirNovoRestaurante(any())).thenReturn(mock(RestauranteDTO.class));
        Restaurante result = restauranteGateway.incluir(restaurante);
        assertNotNull(result);
        verify(dataSource, times(1)).incluirNovoRestaurante(any());
    }

    @Test
    void deveAlterarRestaurante() {
        Restaurante restaurante = mock(Restaurante.class);
        when(dataSource.alterarRestaurante(any())).thenReturn(mock(RestauranteDTO.class));
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
