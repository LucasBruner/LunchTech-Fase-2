package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;

public class AlterarCardapioUseCase {
    private final ICardapioGateway cardapioGateway;
    private final IRestauranteGateway restauranteGateway;

    private AlterarCardapioUseCase(ICardapioGateway cardapioGateway,
                                   IRestauranteGateway restauranteGateway) {
        this.cardapioGateway = cardapioGateway;
        this.restauranteGateway = restauranteGateway;
    }

    public static AlterarCardapioUseCase create(ICardapioGateway cardapioGateway,
                                                IRestauranteGateway restauranteGateway) {
        return new AlterarCardapioUseCase(cardapioGateway, restauranteGateway );
    }

    public Cardapio run(CardapioDTO cardapioAlteracao) {
        Cardapio cardapioExistente = cardapioGateway.buscarProdutoPorNome(cardapioAlteracao.nomeProduto(),
                cardapioAlteracao.restaurante().nomeRestaurante());

        if(cardapioExistente == null) {
            throw new CardapioNaoExisteException("Produto não encontrado!");
        }
        Restaurante restauranteCardapio = restauranteGateway.buscarPorNome(cardapioAlteracao.restaurante().nomeRestaurante());

        Cardapio cardapio = Cardapio.create(cardapioAlteracao.nomeProduto(),
                cardapioAlteracao.descricao(),
                cardapioAlteracao.preco(),
                cardapioAlteracao.apenasPresencial(),
                cardapioAlteracao.fotoPrato(),
                restauranteCardapio);

        Cardapio alterarCardapio = cardapioGateway.alterar(cardapio);

        return alterarCardapio;
    }
}
