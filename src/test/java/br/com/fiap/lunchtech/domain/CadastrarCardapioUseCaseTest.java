package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteCardapioDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.CardapioJaExisteException;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.usecases.cardapio.CadastrarCardapioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CadastrarCardapioUseCaseTest {

    private CadastrarCardapioUseCase cadastrarCardapioUseCase;

    @Mock
    private ICardapioGateway cardapioGateway;
    @Mock
    private IRestauranteGateway restauranteGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cadastrarCardapioUseCase = CadastrarCardapioUseCase.create(cardapioGateway, restauranteGateway);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoJaExisteNoRestaurante() {
        // Arrange
        RestauranteCardapioDTO restauranteDTO = new RestauranteCardapioDTO("restaurante",1L);
        NovoCardapioDTO novoCardapioDTO = new NovoCardapioDTO("produto", "descricao", 10.0, false, "foto", restauranteDTO);
        when(cardapioGateway.buscarProdutoPorNome("produto", "restaurante")).thenReturn(mock(Cardapio.class));

        // Act & Assert
        assertThrows(CardapioJaExisteException.class, () -> cadastrarCardapioUseCase.run(novoCardapioDTO));
    }

    @Test
    void deveCadastrarCardapioComSucesso() {
        // Arrange
        RestauranteCardapioDTO restauranteDTO = new RestauranteCardapioDTO("restaurante", 1L);
        NovoCardapioDTO novoCardapioDTO = new NovoCardapioDTO("produto", "descricao", 10.0, false, "foto", restauranteDTO);
        when(cardapioGateway.buscarProdutoPorNome("produto", "restaurante")).thenThrow(new CardapioNaoExisteException(""));
        when(restauranteGateway.buscarRestaurantePorId(1L)).thenReturn(mock(Restaurante.class));
        when(cardapioGateway.incluir(any(Cardapio.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Cardapio result = cadastrarCardapioUseCase.run(novoCardapioDTO);

        // Assert
        assertNotNull(result);
        assertEquals("produto", result.getNomeProduto());
        verify(cardapioGateway, times(1)).incluir(any(Cardapio.class));
    }
}
