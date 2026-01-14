package br.com.fiap.lunchtech.core.controllers;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioBuscarDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioInfoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteCardapioDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.interfaces.ICardapioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardapioControllerTest {

    private CardapioController cardapioController;

    @Mock
    private ICardapioDataSource cardapioDataSource;
    @Mock
    private IRestauranteDataSource restauranteDataSource;
    @Mock
    private IUsuarioDataSource usuarioDataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardapioController = CardapioController.create(cardapioDataSource, restauranteDataSource, usuarioDataSource);
    }

    private RestauranteDTO createRestauranteDTO() {
        return new RestauranteDTO(1L, "restaurante", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome"));
    }

    @Test
    void deveCadastrarProdutoCardapio() {
        // Arrange
        NovoCardapioDTO novoCardapioDTO = new NovoCardapioDTO("nome", "descricao", 10.0, false, "foto", new RestauranteCardapioDTO("restaurante", 1L));
        when(restauranteDataSource.buscarRestaurantePorId(anyLong())).thenReturn(createRestauranteDTO());
        when(usuarioDataSource.obterUsuarioPorLogin(anyString())).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "DONO_RESTAURANTE", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        when(cardapioDataSource.incluirNovoProdutoCardapio(any(NovoCardapioDTO.class))).thenReturn(new CardapioInfoDTO("nome", "descricao", 10.0, false, "foto", new RestauranteCardapioDTO("restaurante", 1L)));

        // Act
        CardapioDTO result = cardapioController.cadastrarProdutoCardapio(novoCardapioDTO);

        // Assert
        assertNotNull(result);
        assertEquals("nome", result.nomeProduto());
    }

    @Test
    void deveAlterarProdutoCardapio() {
        // Arrange
        CardapioAlteradoDTO cardapioAlteradoDTO = new CardapioAlteradoDTO(1L, "nome", "descricao", 10.0, false, "foto", new RestauranteCardapioDTO("restaurante", 1L));
        when(cardapioDataSource.buscarProdutoPorId(anyLong())).thenReturn(new CardapioDTO(1L, "nome", "desc", 1.0, false, "f", new RestauranteCardapioDTO("restaurante", 1L)));
        when(restauranteDataSource.buscarRestaurantePorId(anyLong())).thenReturn(createRestauranteDTO());
        when(usuarioDataSource.obterUsuarioPorLogin(anyString())).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "DONO_RESTAURANTE", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        when(cardapioDataSource.alterarProdutoCardapio(any(CardapioAlteradoDTO.class))).thenReturn(new CardapioInfoDTO("nome", "descricao", 10.0, false, "foto", new RestauranteCardapioDTO("restaurante", 1L)));

        // Act
        CardapioDTO result = cardapioController.alterarProdutoCardapio(cardapioAlteradoDTO);

        // Assert
        assertNotNull(result);
        assertEquals("nome", result.nomeProduto());
    }

    @Test
    void deveDeletarProdutoCardapio() {
        // Arrange
        when(restauranteDataSource.buscarRestaurantePorId(anyLong())).thenReturn(createRestauranteDTO());
        when(usuarioDataSource.obterUsuarioPorLogin(anyString())).thenReturn(new UsuarioDTO("nome",
                "email@test.com",
                "login",
                "DONO_RESTAURANTE",
                new EnderecoDTO("logradouro",
                        1,
                        "bairro",
                        "cidade",
                        "estado",
                        "01001000")));
        when(cardapioDataSource.buscarProdutoPorId(anyLong())).thenReturn(new CardapioDTO(1L, "nome", "desc", 1.0, false, "f", new RestauranteCardapioDTO("restaurante", 1L)));
        doNothing().when(cardapioDataSource).deletarProduto(1L);

        // Act
        cardapioController.deletarProdutoCardapio(1L);

        // Assert
        verify(cardapioDataSource, times(1)).deletarProduto(1L);
    }

    @Test
    void deveBuscarProduto() {
        // Arrange
        CardapioBuscarDTO cardapioBuscarDTO = new CardapioBuscarDTO("nome", "restaurante");
        when(restauranteDataSource.buscarRestaurantePorId(anyLong())).thenReturn(createRestauranteDTO());
        when(restauranteDataSource.buscarRestaurantePorNome(anyString())).thenReturn(createRestauranteDTO());
        when(usuarioDataSource.obterUsuarioPorLogin(anyString())).thenReturn(new UsuarioDTO("nome",
                "email@test.com",
                "login",
                "DONO_RESTAURANTE",
                new EnderecoDTO("logradouro",
                        1,
                        "bairro",
                        "cidade",
                        "estado",
                        "01001000")));
        when(cardapioDataSource.buscarProdutoPorNome("nome", "restaurante")).thenReturn(new CardapioDTO(1L, "nome", "descricao", 10.0, false, "foto", new RestauranteCardapioDTO("restaurante", 1L)));

        // Act
        CardapioDTO result = cardapioController.buscarProduto(cardapioBuscarDTO);

        // Assert
        assertNotNull(result);
        assertEquals("nome", result.nomeProduto());
    }

    @Test
    void deveBuscarListaDoCardapio() {
        // Arrange
        when(restauranteDataSource.buscarRestaurantePorNome(anyString())).thenReturn(createRestauranteDTO());
        when(usuarioDataSource.obterUsuarioPorLogin(anyString())).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "DONO_RESTAURANTE", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        when(cardapioDataSource.buscarProdutoPorIdRestaurante(anyLong())).thenReturn(Arrays.asList(new CardapioDTO(1L,
                "nome",
                "descricao",
                10.0,
                false,
                "foto",
                new RestauranteCardapioDTO("restaurante", 1L))));

        // Act
        List<CardapioDTO> result = cardapioController.buscarListaDoCardapio("restaurante");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
