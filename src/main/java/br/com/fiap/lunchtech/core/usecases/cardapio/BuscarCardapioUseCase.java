package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioBuscarDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;

public class BuscarCardapioUseCase {
    private ICardapioGateway cardapioGateway;

    private BuscarCardapioUseCase(ICardapioGateway cardapioGateway) {
        this.cardapioGateway = cardapioGateway;
    }

    public static BuscarCardapioUseCase create(ICardapioGateway cardapioGateway) {
        return new BuscarCardapioUseCase(cardapioGateway);
    }

    public Cardapio run(CardapioBuscarDTO cardapioBuscarDTO) {
        Cardapio cardapio = cardapioGateway.buscarProdutoPorNome(cardapioBuscarDTO.nomeProduto(), cardapioBuscarDTO.nomeRestaurante());

        if(cardapio == null) {
            throw new CardapioNaoExisteException("Produto não encontrado.");
        }

        return cardapio;
    }
}
