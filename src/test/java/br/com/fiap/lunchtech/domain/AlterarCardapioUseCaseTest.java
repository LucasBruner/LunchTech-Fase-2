package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteCardapioDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.CardapioJaExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.usecases.cardapio.AlterarCardapioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AlterarCardapioUseCaseTest {

    private AlterarCardapioUseCase alterarCardapioUseCase;

    @Mock
    private ICardapioGateway cardapioGateway;
    @Mock
    private IRestauranteGateway restauranteGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alterarCardapioUseCase = AlterarCardapioUseCase.create(cardapioGateway, restauranteGateway);
    }

    @Test
    void deveLancarExcecaoQuandoNovoNomeProdutoJaExiste() {
        // Arrange
        RestauranteCardapioDTO restauranteDTO = new RestauranteCardapioDTO("restaurante",1L);
        CardapioAlteradoDTO cardapioAlteradoDTO = new CardapioAlteradoDTO(1L,
                "novo_nome",
                "descricao",
                10.0,
                false,
                "foto",
                restauranteDTO);
        Cardapio cardapioExistente = mock(Cardapio.class);
        when(cardapioExistente.getNomeProduto()).thenReturn("nome_antigo");
        when(cardapioGateway.buscarProdutoPorId(1L)).thenReturn(cardapioExistente);
        when(cardapioGateway.buscarProdutoPorNome("novo_nome", "restaurante")).thenReturn(mock(Cardapio.class));

        // Act & Assert
        assertThrows(CardapioJaExisteException.class, () -> alterarCardapioUseCase.run(cardapioAlteradoDTO));
    }

    @Test
    void deveAlterarCardapioComSucesso() {
        // Arrange
        RestauranteCardapioDTO restauranteDTO = new RestauranteCardapioDTO("restaurante", 1L);
        CardapioAlteradoDTO cardapioAlteradoDTO = new CardapioAlteradoDTO(1L,
                "novo_nome",
                "descricao",
                10.0,
                false,
                "foto", restauranteDTO);
        Cardapio cardapioExistente = mock(Cardapio.class);
        Restaurante restauranteMock = mock(Restaurante.class);
        when(restauranteMock.getNome()).thenReturn("restaurante");
        when(cardapioExistente.getNomeProduto()).thenReturn("nome_antigo");
        when(cardapioGateway.buscarProdutoPorId(1L)).thenReturn(cardapioExistente);
        when(cardapioGateway.buscarProdutoPorNome("novo_nome", "restaurante")).thenReturn(null);
        when(restauranteGateway.buscarRestaurantePorId(1L)).thenReturn(restauranteMock);
        when(cardapioGateway.alterar(any(Cardapio.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Cardapio result = alterarCardapioUseCase.run(cardapioAlteradoDTO);

        // Assert
        assertNotNull(result);
        assertEquals("novo_nome", result.getNomeProduto());
        verify(cardapioGateway, times(1)).alterar(any(Cardapio.class));
    }
}
