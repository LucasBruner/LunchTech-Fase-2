package br.com.fiap.lunchtech.core.gateway;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioInfoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteCardapioDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardapioGatewayTest {

    private CardapioGateway cardapioGateway;

    @Mock
    private ICardapioDataSource dataSource;
    @Mock
    private RestauranteGateway restauranteGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardapioGateway = CardapioGateway.create(dataSource, restauranteGateway);
    }

    @Test
    void deveBuscarProdutoPorNome() {
        Restaurante restaurante = mock(Restaurante.class);
        when(restaurante.getNome()).thenReturn("restaurante");
        when(dataSource.buscarProdutoPorNome("nome", "restaurante")).thenReturn(new CardapioDTO(1L, "nome", "desc", 1.0, false, "foto", new RestauranteCardapioDTO("restaurante", 1L)));
        when(restauranteGateway.buscarRestaurantePorId(anyLong())).thenReturn(restaurante);
        Cardapio result = cardapioGateway.buscarProdutoPorNome("nome", "restaurante");
        assertNotNull(result);
    }

    @Test
    void deveLancarExcecaoSeProdutoNaoEncontrado() {
        when(dataSource.buscarProdutoPorNome("invalido", "restaurante")).thenThrow(new CardapioNaoExisteException("Produto não encontrado."));
        assertThrows(CardapioNaoExisteException.class, () -> cardapioGateway.buscarProdutoPorNome("invalido", "restaurante"));
    }

    @Test
    void deveBuscarProdutosPorRestaurante() {
        Restaurante restaurante = mock(Restaurante.class);
        when(restaurante.getId()).thenReturn(1L);
        when(restaurante.getNome()).thenReturn("restaurante");
        when(restauranteGateway.buscarPorNome(anyString())).thenReturn(restaurante);
        when(dataSource.buscarProdutoPorIdRestaurante(anyLong())).thenReturn(Arrays.asList(new CardapioDTO(1L, "nome", "desc", 1.0, false, "foto", new RestauranteCardapioDTO("restaurante", 1L))));
        List<Cardapio> result = cardapioGateway.buscarProdutosPorRestaurante("restaurante");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void deveIncluirProduto() {
        Cardapio cardapio = mock(Cardapio.class);
        Restaurante restaurante = mock(Restaurante.class);
        when(restaurante.getNome()).thenReturn("restaurante");
        when(cardapio.getRestaurante()).thenReturn(restaurante);
        when(cardapio.getNomeProduto()).thenReturn("nome");
        when(dataSource.incluirNovoProdutoCardapio(any())).thenReturn(new CardapioInfoDTO("nome", "desc", 1.0, false, "foto", new RestauranteCardapioDTO("restaurante", 1L)));
        Cardapio result = cardapioGateway.incluir(cardapio);
        assertNotNull(result);
        verify(dataSource, times(1)).incluirNovoProdutoCardapio(any());
    }

    @Test
    void deveAlterarProduto() {
        Cardapio cardapio = mock(Cardapio.class);
        Restaurante restaurante = mock(Restaurante.class);
        when(restaurante.getNome()).thenReturn("restaurante");
        when(cardapio.getRestaurante()).thenReturn(restaurante);
        when(cardapio.getNomeProduto()).thenReturn("nome");
        when(dataSource.alterarProdutoCardapio(any())).thenReturn(new CardapioInfoDTO("nome", "desc", 1.0, false, "foto", new RestauranteCardapioDTO("restaurante", 1L)));
        Cardapio result = cardapioGateway.alterar(cardapio);
        assertNotNull(result);
        verify(dataSource, times(1)).alterarProdutoCardapio(any());
    }

    @Test
    void deveDeletarProduto() {
        doNothing().when(dataSource).deletarProduto(1L);
        cardapioGateway.deletar(1L);
        verify(dataSource, times(1)).deletarProduto(1L);
    }
}
