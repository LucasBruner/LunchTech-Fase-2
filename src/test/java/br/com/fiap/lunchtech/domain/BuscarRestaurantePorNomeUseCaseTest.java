package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.usecases.restaurante.BuscarRestaurantePorNomeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarRestaurantePorNomeUseCaseTest {

    private BuscarRestaurantePorNomeUseCase buscarRestaurantePorNomeUseCase;

    @Mock
    private IRestauranteGateway restauranteGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        buscarRestaurantePorNomeUseCase = BuscarRestaurantePorNomeUseCase.create(restauranteGateway);
    }

    @Test
    void deveRetornarRestauranteQuandoEncontrado() {
        // Arrange
        String nome = "restaurante";
        Restaurante restaurante = mock(Restaurante.class);
        when(restauranteGateway.buscarPorNome(nome)).thenReturn(restaurante);

        // Act
        Restaurante result = buscarRestaurantePorNomeUseCase.run(nome);

        // Assert
        assertNotNull(result);
        verify(restauranteGateway, times(1)).buscarPorNome(nome);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        // Arrange
        String nome = "invalido";
        when(restauranteGateway.buscarPorNome(nome)).thenReturn(null);

        // Act & Assert
        assertThrows(RestauranteNaoEncontradoException.class, () -> buscarRestaurantePorNomeUseCase.run(nome));
        verify(restauranteGateway, times(1)).buscarPorNome(nome);
    }
}
