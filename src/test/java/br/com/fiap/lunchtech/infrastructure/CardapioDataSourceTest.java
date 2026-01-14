package br.com.fiap.lunchtech.infrastructure;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioInfoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteCardapioDTO;
import br.com.fiap.lunchtech.infra.database.datasource.CardapioDataSource;
import br.com.fiap.lunchtech.infra.database.datasource.RestauranteDataSource;
import br.com.fiap.lunchtech.infra.database.entities.CardapioEntity;
import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import br.com.fiap.lunchtech.infra.database.repositories.ICardapioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardapioDataSourceTest {

    private CardapioDataSource cardapioDataSource;

    @Mock
    private ICardapioRepository cardapioRepository;
    @Mock
    private RestauranteDataSource restauranteDataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardapioDataSource = new CardapioDataSource(cardapioRepository, restauranteDataSource);
    }

    private CardapioEntity createCardapioEntity() {
        CardapioEntity cardapioEntity = new CardapioEntity();
        cardapioEntity.setPreco(10.0);
        cardapioEntity.setRestaurante(new RestauranteEntity());
        return cardapioEntity;
    }

    @Test
    void deveIncluirNovoProdutoCardapio() {
        // Arrange
        NovoCardapioDTO novoCardapioDTO = new NovoCardapioDTO("nome", "descricao", 10.0, false, "foto", new RestauranteCardapioDTO( "restaurante",1L));
        when(restauranteDataSource.buscarRestauranteEntity("restaurante")).thenReturn(new RestauranteEntity());
        when(cardapioRepository.save(any(CardapioEntity.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        CardapioInfoDTO result = cardapioDataSource.incluirNovoProdutoCardapio(novoCardapioDTO);

        // Assert
        assertNotNull(result);
        assertEquals("nome", result.nomeProduto());
        verify(cardapioRepository, times(1)).save(any(CardapioEntity.class));
    }

    @Test
    void deveAlterarProdutoCardapio() {
        // Arrange
        CardapioAlteradoDTO cardapioAlteradoDTO = new CardapioAlteradoDTO(1L, "nome", "descricao", 10.0, false, "foto", new RestauranteCardapioDTO("restaurante",1L));
        when(restauranteDataSource.buscarRestauranteEntity("restaurante")).thenReturn(new RestauranteEntity());
        when(cardapioRepository.save(any(CardapioEntity.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        CardapioInfoDTO result = cardapioDataSource.alterarProdutoCardapio(cardapioAlteradoDTO);

        // Assert
        assertNotNull(result);
        assertEquals("nome", result.nomeProduto());
        verify(cardapioRepository, times(1)).save(any(CardapioEntity.class));
    }

    @Test
    void deveDeletarProduto() {
        // Arrange
        CardapioEntity cardapioEntity = createCardapioEntity();
        when(cardapioRepository.findById(1L)).thenReturn(Optional.of(cardapioEntity));
        doNothing().when(cardapioRepository).delete(cardapioEntity);

        // Act
        cardapioDataSource.deletarProduto(1L);

        // Assert
        verify(cardapioRepository, times(1)).delete(cardapioEntity);
    }

    @Test
    void deveBuscarProdutoPorNome() {
        // Arrange
        RestauranteEntity restauranteEntity = new RestauranteEntity();
        CardapioEntity cardapioEntity = createCardapioEntity();
        when(restauranteDataSource.buscarRestaurante("restaurante")).thenReturn(restauranteEntity);
        when(cardapioRepository.findByNomeProdutoAndRestauranteId("nome", restauranteEntity)).thenReturn(cardapioEntity);
        when(restauranteDataSource.toRestauranteCardapioDTO(any())).thenReturn(new RestauranteCardapioDTO("restaurante",1L));

        // Act
        CardapioDTO result = cardapioDataSource.buscarProdutoPorNome("nome", "restaurante");

        // Assert
        assertNotNull(result);
    }

    @Test
    void deveBuscarProdutoPorId() {
        // Arrange
        CardapioEntity cardapioEntity = createCardapioEntity();
        when(cardapioRepository.findById(1L)).thenReturn(Optional.of(cardapioEntity));
        when(restauranteDataSource.toRestauranteCardapioDTO(any())).thenReturn(new RestauranteCardapioDTO("restaurante",1L));

        // Act
        CardapioDTO result = cardapioDataSource.buscarProdutoPorId(1L);

        // Assert
        assertNotNull(result);
    }



    @Test
    void deveBuscarProdutoPorIdRestaurante() {
        // Arrange
        CardapioEntity cardapioEntity = createCardapioEntity();
        when(cardapioRepository.findAllByRestauranteId(1L)).thenReturn(Arrays.asList(cardapioEntity));
        when(restauranteDataSource.toRestauranteCardapioDTO(any())).thenReturn(new RestauranteCardapioDTO("restaurante",1L));

        // Act
        List<CardapioDTO> result = cardapioDataSource.buscarProdutoPorIdRestaurante(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
