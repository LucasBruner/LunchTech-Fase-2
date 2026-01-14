package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioBuscarDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarCardapioUseCaseTest {

    private BuscarCardapioUseCase buscarCardapioUseCase;

    @Mock
    private ICardapioGateway cardapioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        buscarCardapioUseCase = BuscarCardapioUseCase.create(cardapioGateway);
    }

    @Test
    void deveRetornarCardapioQuandoEncontrado() {
        // Arrange
        CardapioBuscarDTO cardapioBuscarDTO = new CardapioBuscarDTO("produto", "restaurante");
        Cardapio cardapio = mock(Cardapio.class);
        when(cardapioGateway.buscarProdutoPorNome("produto", "restaurante")).thenReturn(cardapio);

        // Act
        Cardapio result = buscarCardapioUseCase.run(cardapioBuscarDTO);

        // Assert
        assertNotNull(result);
        verify(cardapioGateway, times(1)).buscarProdutoPorNome("produto", "restaurante");
    }

    @Test
    void deveLancarExcecaoQuandoCardapioNaoEncontrado() {
        // Arrange
        CardapioBuscarDTO cardapioBuscarDTO = new CardapioBuscarDTO("invalido", "restaurante");
        when(cardapioGateway.buscarProdutoPorNome("invalido", "restaurante")).thenReturn(null);

        // Act & Assert
        assertThrows(CardapioNaoExisteException.class, () -> buscarCardapioUseCase.run(cardapioBuscarDTO));
        verify(cardapioGateway, times(1)).buscarProdutoPorNome("invalido", "restaurante");
    }
}
