package br.com.fiap.lunchtech.core.controllers;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioBuscarDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDelecaoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.gateway.CardapioGateway;
import br.com.fiap.lunchtech.core.gateway.RestauranteGateway;
import br.com.fiap.lunchtech.core.interfaces.ICardapioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.presenters.CardapioPresenter;
import br.com.fiap.lunchtech.core.usecases.cardapio.AlterarCardapioUseCase;
import br.com.fiap.lunchtech.core.usecases.cardapio.BuscarCardapioUseCase;
import br.com.fiap.lunchtech.core.usecases.cardapio.CadastrarCardapioUseCase;
import br.com.fiap.lunchtech.core.usecases.cardapio.DeletarCardapioUseCase;

public class CardapioController {
    private ICardapioDataSource cardapioDataSource;
    private IRestauranteDataSource restauranteDataSource;

    private CardapioController(ICardapioDataSource cardapioDataSource, IRestauranteDataSource restauranteDataSource) {
        this.cardapioDataSource = cardapioDataSource;
        this.restauranteDataSource = restauranteDataSource;
    }

    public static CardapioController create (ICardapioDataSource cardapioDataSource, IRestauranteDataSource restauranteDataSource) {
        return new CardapioController(cardapioDataSource, restauranteDataSource);
    }

    public CardapioDTO cadastrarProdutoCardapio(NovoCardapioDTO novoCardapioDTO) {
        var cardapioGateway = CardapioGateway.create(cardapioDataSource);
        var restauranteGateway = RestauranteGateway.create(restauranteDataSource);
        var cadastrarCardapioUseCase = CadastrarCardapioUseCase.create(cardapioGateway, restauranteGateway);

        var novoCardapio = cadastrarCardapioUseCase.run(novoCardapioDTO);
        var cardapioPresenter = CardapioPresenter.toDto(novoCardapio);

        return cardapioPresenter;
    }

    public CardapioDTO alterarProdutoCardapio(CardapioAlteradoDTO cardapioAlteradoDTO) {
        var cardapioGateway = CardapioGateway.create(cardapioDataSource);
        var restauranteGateway = RestauranteGateway.create(restauranteDataSource);
        var alterarCardapioUseCase = AlterarCardapioUseCase.create(cardapioGateway, restauranteGateway);

        var cardapioAlterado = alterarCardapioUseCase.run(cardapioAlteradoDTO);
        var cardapioPresenter = CardapioPresenter.toDto(cardapioAlterado);

        return cardapioPresenter;
    }

    public void deletarProdutoCardapio(CardapioDelecaoDTO cardapioDelecaoDTO) {
        var cardapioGateway = CardapioGateway.create(cardapioDataSource);
        var deletarCardapioUseCase = DeletarCardapioUseCase.create(cardapioGateway);

        deletarCardapioUseCase.run(cardapioDelecaoDTO);
    }

    public CardapioDTO buscarProduto (CardapioBuscarDTO cardapioBuscarDTO) {
        var cardapioGateway = CardapioGateway.create(cardapioDataSource);
        var buscarCardapioUseCase = BuscarCardapioUseCase.create(cardapioGateway);

        var cardapioBuscado = buscarCardapioUseCase.run(cardapioBuscarDTO);
        var cardapioPresenter = CardapioPresenter.toDto(cardapioBuscado);

        return cardapioPresenter;
    }
}
