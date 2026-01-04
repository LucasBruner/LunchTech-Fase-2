package br.com.fiap.lunchtech.core.usecases.cardapio;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.CardapioJaExisteException;
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

    public Cardapio run(CardapioDTO novoCardapioDTO) {
        Cardapio cardapioExistente = cardapioGateway.buscarProdutoPorNome(novoCardapioDTO.nomeProduto(),
                novoCardapioDTO.restaurante().nomeRestaurante());

        if (cardapioExistente != null) {
            throw new CardapioJaExisteException("O produto solicitado para adicionar já existe!");
        }

        Restaurante restaurante = restauranteGateway.buscarPorNome(novoCardapioDTO.restaurante().nomeRestaurante());

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
