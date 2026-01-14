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
import br.com.fiap.lunchtech.infra.http.controller.json.CardapioItemJson;
import br.com.fiap.lunchtech.infra.http.controller.json.CardapioJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
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
        CardapioItemJson cardapioItemJson = new CardapioItemJson();
        setField(cardapioItemJson, "nomeProduto", "Pizza");
        setField(cardapioItemJson, "nomeRestaurante", "Restaurante");

        RestauranteCardapioDTO restauranteCardapioDTO = new RestauranteCardapioDTO("Restaurante", 1L);
        CardapioDTO cardapioDTO = new CardapioDTO(1L, "Pizza", "Calabresa", 30.0, false, "foto.jpg", restauranteCardapioDTO);
        when(cardapioController.buscarProduto(any(CardapioBuscarDTO.class))).thenReturn(cardapioDTO);

        ResponseEntity<CardapioDTO> response = cardapioApiController.buscarPorNome(cardapioItemJson);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Pizza", response.getBody().nomeProduto());
    }

    @Test
    void criarItemCardapio() {
        CardapioJson cardapioJson = new CardapioJson();
        setField(cardapioJson, "nomeProduto", "Pizza");
        setField(cardapioJson, "descricao", "Calabresa");
        setField(cardapioJson, "preco", 30.0);
        setField(cardapioJson, "apenasPresencial", false);
        setField(cardapioJson, "fotoPrato", "foto.jpg");
        setField(cardapioJson, "restauranteId", 1L);

        RestauranteCardapioDTO restauranteCardapioDTO = new RestauranteCardapioDTO("Restaurante", 1L);
        CardapioDTO cardapioDTO = new CardapioDTO(1L, "Pizza", "Calabresa", 30.0, false, "foto.jpg", restauranteCardapioDTO);
        when(cardapioController.cadastrarProdutoCardapio(any(NovoCardapioDTO.class))).thenReturn(cardapioDTO);

        ResponseEntity<Void> response = cardapioApiController.criarItemCardapio(cardapioJson);

        assertEquals(201, response.getStatusCode().value());
        
        ArgumentCaptor<NovoCardapioDTO> captor = ArgumentCaptor.forClass(NovoCardapioDTO.class);
        verify(cardapioController).cadastrarProdutoCardapio(captor.capture());
        assertEquals("Pizza", captor.getValue().nomeProduto());
    }

    @Test
    void alterarRestaurante() {
        CardapioJson cardapioJson = new CardapioJson();
        setField(cardapioJson, "id", 1L);
        setField(cardapioJson, "nomeProduto", "Pizza");
        setField(cardapioJson, "descricao", "Calabresa");
        setField(cardapioJson, "preco", 35.0);
        setField(cardapioJson, "apenasPresencial", false);
        setField(cardapioJson, "fotoPrato", "foto.jpg");
        setField(cardapioJson, "restauranteId", 1L);

        RestauranteCardapioDTO restauranteCardapioDTO = new RestauranteCardapioDTO("Restaurante", 1L);
        CardapioDTO cardapioDTO = new CardapioDTO(1L, "Pizza", "Calabresa", 35.0, false, "foto.jpg", restauranteCardapioDTO);
        when(cardapioController.alterarProdutoCardapio(any(CardapioAlteradoDTO.class))).thenReturn(cardapioDTO);

        ResponseEntity<Void> response = cardapioApiController.alterarRestaurante(cardapioJson);

        assertEquals(200, response.getStatusCode().value());
        
        ArgumentCaptor<CardapioAlteradoDTO> captor = ArgumentCaptor.forClass(CardapioAlteradoDTO.class);
        verify(cardapioController).alterarProdutoCardapio(captor.capture());
        assertEquals("Pizza", captor.getValue().nomeProduto());
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

    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}