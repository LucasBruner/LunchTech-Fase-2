package br.com.fiap.lunchtech.domain.gateway;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.gateway.CardapioGateway;
import br.com.fiap.lunchtech.core.gateway.RestauranteGateway;
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
        when(dataSource.buscarProdutoPorNome("nome", "restaurante")).thenReturn(mock(CardapioDTO.class));
        Cardapio result = cardapioGateway.buscarProdutoPorNome("nome", "restaurante");
        assertNotNull(result);
    }

    @Test
    void deveRetornarNuloSeProdutoNaoEncontrado() {
        when(dataSource.buscarProdutoPorNome("invalido", "restaurante")).thenReturn(null);
        Cardapio result = cardapioGateway.buscarProdutoPorNome("invalido", "restaurante");
        assertNull(result);
    }

    @Test
    void deveBuscarProdutosPorRestaurante() {
        when(dataSource.buscarProdutoPorIdRestaurante(anyLong())).thenReturn(Arrays.asList(mock(CardapioDTO.class)));
        List<Cardapio> result = cardapioGateway.buscarProdutosPorRestaurante("restaurante");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void deveIncluirProduto() {
        Cardapio cardapio = mock(Cardapio.class);
        when(dataSource.incluirNovoProdutoCardapio(any())).thenReturn(mock(CardapioDTO.class));
        Cardapio result = cardapioGateway.incluir(cardapio);
        assertNotNull(result);
        verify(dataSource, times(1)).incluirNovoProdutoCardapio(any());
    }

    @Test
    void deveAlterarProduto() {
        Cardapio cardapio = mock(Cardapio.class);
        when(dataSource.alterarProdutoCardapio(any())).thenReturn(mock(CardapioDTO.class));
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
