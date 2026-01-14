package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeletarCardapioUseCaseTest {

    private DeletarCardapioUseCase deletarCardapioUseCase;

    @Mock
    private ICardapioGateway cardapioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deletarCardapioUseCase = DeletarCardapioUseCase.create(cardapioGateway);
    }

    @Test
    void deveLancarExcecaoQuandoCardapioNaoEncontrado() {
        // Arrange
        Long id = 1L;
        when(cardapioGateway.buscarProdutoPorId(id)).thenReturn(null);

        // Act & Assert
        assertThrows(CardapioNaoExisteException.class, () -> deletarCardapioUseCase.run(id));
    }

    @Test
    void deveDeletarCardapioComSucesso() {
        // Arrange
        Long id = 1L;
        when(cardapioGateway.buscarProdutoPorId(id)).thenReturn(mock(Cardapio.class));
        doNothing().when(cardapioGateway).deletar(id);

        // Act
        deletarCardapioUseCase.run(id);

        // Assert
        verify(cardapioGateway, times(1)).deletar(id);
    }
}
