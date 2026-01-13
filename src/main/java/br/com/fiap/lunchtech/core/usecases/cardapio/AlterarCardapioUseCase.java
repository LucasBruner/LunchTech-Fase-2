package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.CardapioJaExisteException;
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

    public Cardapio run(CardapioAlteradoDTO cardapioAlteracao) {
        Cardapio cardapioExistente = cardapioGateway.buscarProdutoPorId(cardapioAlteracao.id());

        Cardapio cardapioExiste;
        try {
            cardapioExiste = cardapioGateway.buscarProdutoPorNome(cardapioAlteracao.nomeProduto(),
                    cardapioAlteracao.restaurante().nomeRestaurante());
        } catch (CardapioNaoExisteException _) {
            cardapioExiste = null;
        }

        if(cardapioExiste != null && !cardapioExistente.getNomeProduto().equals(cardapioAlteracao.nomeProduto())) {
            throw new CardapioJaExisteException("Produto já está cadastrado para esse restaurante!");
        }

        Restaurante restauranteCardapio = restauranteGateway.buscarRestaurantePorId(cardapioAlteracao.restaurante().id());

        Cardapio cardapio = Cardapio.create(cardapioAlteracao.id(),
                cardapioAlteracao.nomeProduto(),
                cardapioAlteracao.descricao(),
                cardapioAlteracao.preco(),
                cardapioAlteracao.apenasPresencial(),
                cardapioAlteracao.fotoPrato(),
                restauranteCardapio);

        return cardapioGateway.alterar(cardapio);
    }
}
