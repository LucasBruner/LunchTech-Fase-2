package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarListaCardapioUseCaseTest {

    private BuscarListaCardapioUseCase buscarListaCardapioUseCase;

    @Mock
    private ICardapioGateway cardapioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        buscarListaCardapioUseCase = BuscarListaCardapioUseCase.create(cardapioGateway);
    }

    @Test
    void deveRetornarListaDeCardapioQuandoEncontrados() {
        // Arrange
        String restaurante = "restaurante";
        List<Cardapio> cardapios = Arrays.asList(mock(Cardapio.class), mock(Cardapio.class));
        when(cardapioGateway.buscarProdutosPorRestaurante(restaurante)).thenReturn(cardapios);

        // Act
        List<Cardapio> result = buscarListaCardapioUseCase.run(restaurante);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(cardapioGateway, times(1)).buscarProdutosPorRestaurante(restaurante);
    }

    @Test
    void deveLancarExcecaoQuandoNenhumCardapioEncontrado() {
        // Arrange
        String restaurante = "invalido";
        when(cardapioGateway.buscarProdutosPorRestaurante(restaurante)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(CardapioNaoExisteException.class, () -> buscarListaCardapioUseCase.run(restaurante));
        verify(cardapioGateway, times(1)).buscarProdutosPorRestaurante(restaurante);
    }
}
