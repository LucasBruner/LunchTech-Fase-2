package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.CardapioController;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioBuscarDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteCardapioDTO;
import br.com.fiap.lunchtech.core.interfaces.ICardapioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CardapioApiControllerTest {

    private CardapioApiController cardapioApiController;

    @Mock
    private ICardapioDataSource cardapioDataSource;

    @Mock
    private IRestauranteDataSource restauranteDataSource;

    @Mock
    private IUsuarioDataSource usuarioDataSource;

    @Mock
    private CardapioController cardapioController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        cardapioApiController = new CardapioApiController(cardapioDataSource, restauranteDataSource, usuarioDataSource);
        Field controllerField = CardapioApiController.class.getDeclaredField("cardapioController");
        controllerField.setAccessible(true);
        controllerField.set(cardapioApiController, cardapioController);
    }

    @Test
    void buscarPorNome() {
        CardapioBuscarDTO cardapioBuscarDTO = new CardapioBuscarDTO("Pizza", "Restaurante");
        RestauranteCardapioDTO restauranteCardapioDTO = new RestauranteCardapioDTO("Restaurante", 1L);
        CardapioDTO cardapioDTO = new CardapioDTO(1L, "Pizza", "Calabresa", 30.0, false, "foto.jpg", restauranteCardapioDTO);
        when(cardapioController.buscarProduto(cardapioBuscarDTO)).thenReturn(cardapioDTO);

        ResponseEntity<CardapioDTO> response = cardapioApiController.buscarPorNome(cardapioBuscarDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Pizza", response.getBody().nomeProduto());
    }

    @Test
    void criarItemCardapio() {
        RestauranteCardapioDTO restauranteCardapioDTO = new RestauranteCardapioDTO("Restaurante", 1L);
        NovoCardapioDTO novoCardapioDTO = new NovoCardapioDTO("Pizza", "Calabresa", 30.0, false, "foto.jpg", restauranteCardapioDTO);
        CardapioDTO cardapioDTO = new CardapioDTO(1L, "Pizza", "Calabresa", 30.0, false, "foto.jpg", restauranteCardapioDTO);
        when(cardapioController.cadastrarProdutoCardapio(novoCardapioDTO)).thenReturn(cardapioDTO);

        ResponseEntity<Void> response = cardapioApiController.criarItemCardapio(novoCardapioDTO);

        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void alterarRestaurante() {
        RestauranteCardapioDTO restauranteCardapioDTO = new RestauranteCardapioDTO("Restaurante", 1L);
        CardapioAlteradoDTO cardapioAlteradoDTO = new CardapioAlteradoDTO(1L, "Pizza", "Calabresa", 35.0, false, "foto.jpg", restauranteCardapioDTO);
        CardapioDTO cardapioDTO = new CardapioDTO(1L, "Pizza", "Calabresa", 35.0, false, "foto.jpg", restauranteCardapioDTO);
        when(cardapioController.alterarProdutoCardapio(cardapioAlteradoDTO)).thenReturn(cardapioDTO);

        ResponseEntity<Void> response = cardapioApiController.alterarRestaurante(cardapioAlteradoDTO);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void deletarRestaurante() {
        doNothing().when(cardapioController).deletarProdutoCardapio(1L);

        ResponseEntity<Void> response = cardapioApiController.deletarRestaurante(1L);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void buscarCardapioRestaurante() {
        RestauranteCardapioDTO restauranteCardapioDTO = new RestauranteCardapioDTO("Restaurante", 1L);
        CardapioDTO cardapioDTO = new CardapioDTO(1L, "Pizza", "Calabresa", 30.0, false, "foto.jpg", restauranteCardapioDTO);
        when(cardapioController.buscarListaDoCardapio("Restaurante")).thenReturn(Collections.singletonList(cardapioDTO));

        ResponseEntity<List<CardapioDTO>> response = cardapioApiController.buscarCardapioRestaurante("Restaurante");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }
}