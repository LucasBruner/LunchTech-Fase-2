package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;

public class AlterarCardapioUseCase {
    private final ICardapioGateway cardapioGateway;

    private AlterarCardapioUseCase(ICardapioGateway cardapioGateway) {
        this.cardapioGateway = cardapioGateway;
    }

    public static AlterarCardapioUseCase create(ICardapioGateway cardapioGateway) {
        return new AlterarCardapioUseCase(cardapioGateway);
    }

    public Cardapio run() {

    }
}
