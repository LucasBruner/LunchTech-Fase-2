package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.CardapioJaExisteException;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;

public class CadastrarCardapioUseCase {
    private final ICardapioGateway cardapioGateway;
    private final IRestauranteGateway restauranteGateway;

    private CadastrarCardapioUseCase(ICardapioGateway cardapioGateway, IRestauranteGateway restauranteGateway) {
        this.cardapioGateway = cardapioGateway;
        this.restauranteGateway = restauranteGateway;
    }

    public static CadastrarCardapioUseCase create(ICardapioGateway cardapioGateway, IRestauranteGateway restauranteGateway) {
        return new CadastrarCardapioUseCase(cardapioGateway, restauranteGateway);
    }

    public Cardapio run(NovoCardapioDTO novoCardapioDTO) {
        Cardapio cardapioExistente;

        try{
            cardapioExistente = cardapioGateway.buscarProdutoPorNome(novoCardapioDTO.nomeProduto(),
                    novoCardapioDTO.restaurante().nomeRestaurante());
        } catch (CardapioNaoExisteException e) {
            cardapioExistente = null;
        }

        if (cardapioExistente != null) {
            throw new CardapioJaExisteException("O produto solicitado para adicionar já existe nesse restaurante!");
        }

        Restaurante restaurante = restauranteGateway.buscarRestaurantePorId(novoCardapioDTO.restaurante().id());

        Cardapio cardapioAlteracao = Cardapio.create(novoCardapioDTO.nomeProduto(),
                novoCardapioDTO.descricao(),
                novoCardapioDTO.preco(),
                novoCardapioDTO.apenasPresencial(),
                novoCardapioDTO.fotoPrato(),
                restaurante);

        Cardapio novoCardapio = cardapioGateway.incluir(cardapioAlteracao);

        return novoCardapio;
    }
}
