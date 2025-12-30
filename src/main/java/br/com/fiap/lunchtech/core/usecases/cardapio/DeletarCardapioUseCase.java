package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;

public class DeletarCardapioUseCase {
    private final ICardapioGateway cardapioGateway;

    private DeletarCardapioUseCase(ICardapioGateway cardapioGateway) {
        this.cardapioGateway = cardapioGateway;
    }

    public static DeletarCardapioUseCase create(ICardapioGateway cardapioGateway) {
        return new DeletarCardapioUseCase(cardapioGateway);
    }

    public void run() {
        cardapioGateway.deletar(nomeProduto, nomeRestaurante);
    }
}
