package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;

import java.util.List;

public class BuscarListaCardapioUseCase {
    private ICardapioGateway cardapioGateway;

    private BuscarListaCardapioUseCase(ICardapioGateway cardapioGateway) {
        this.cardapioGateway = cardapioGateway;
    }

    public static BuscarListaCardapioUseCase create(ICardapioGateway cardapioGateway) {
        return new BuscarListaCardapioUseCase(cardapioGateway);
    }

    public List<Cardapio> run(String restaurante) {
        List<Cardapio> itensCardapio = cardapioGateway.buscarProdutosPorRestaurante(restaurante);

        if(itensCardapio.isEmpty()) {
            throw new CardapioNaoExisteException("Produtos não encontrado.");
        }

        return itensCardapio;
    }
}
