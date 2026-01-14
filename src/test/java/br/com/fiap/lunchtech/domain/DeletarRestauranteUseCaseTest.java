package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.usecases.restaurante.DeletarRestauranteUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeletarRestauranteUseCaseTest {

    private DeletarRestauranteUseCase deletarRestauranteUseCase;

    @Mock
    private IRestauranteGateway restauranteGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deletarRestauranteUseCase = DeletarRestauranteUseCase.create(restauranteGateway);
    }

    @Test
    void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {
        // Arrange
        String nome = "invalido";
        when(restauranteGateway.buscarPorNome(nome)).thenReturn(null);

        // Act & Assert
        assertThrows(RestauranteNaoEncontradoException.class, () -> deletarRestauranteUseCase.run(nome));
    }

    @Test
    void deveDeletarRestauranteComSucesso() {
        // Arrange
        String nome = "restaurante";
        when(restauranteGateway.buscarPorNome(nome)).thenReturn(mock(Restaurante.class));
        doNothing().when(restauranteGateway).deletar(nome);

        // Act
        deletarRestauranteUseCase.run(nome);

        // Assert
        verify(restauranteGateway, times(1)).deletar(nome);
    }
}
