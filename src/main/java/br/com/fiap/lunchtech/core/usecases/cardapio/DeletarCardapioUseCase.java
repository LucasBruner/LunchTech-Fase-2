package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;

public class DeletarCardapioUseCase {
    private final ICardapioGateway cardapioGateway;

    private DeletarCardapioUseCase(ICardapioGateway cardapioGateway) {
        this.cardapioGateway = cardapioGateway;
    }

    public static DeletarCardapioUseCase create(ICardapioGateway cardapioGateway) {
        return new DeletarCardapioUseCase(cardapioGateway);
    }
    public void run(Long id) {
        Cardapio cardapio = cardapioGateway.buscarProdutoPorId(id);

        if(cardapio == null) {
            throw new CardapioNaoExisteException("Produto não encontrado.");
        }

        cardapioGateway.deletar(id);
    }
}
