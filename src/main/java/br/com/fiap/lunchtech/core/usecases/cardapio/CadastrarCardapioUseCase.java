package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;

public class CadastrarCardapioUseCase {
    private final ICardapioGateway cardapioGateway;

    private CadastrarCardapioUseCase(ICardapioGateway cardapioGateway) {
        this.cardapioGateway = cardapioGateway;
    }

    public static CadastrarCardapioUseCase create(ICardapioGateway cardapioGateway) {
        return new CadastrarCardapioUseCase(cardapioGateway);
    }

    public Cardapio run() {
    }
}
