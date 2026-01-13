package br.com.fiap.lunchtech.core.controllers;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioBuscarDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.gateway.CardapioGateway;
import br.com.fiap.lunchtech.core.gateway.RestauranteGateway;
import br.com.fiap.lunchtech.core.gateway.UsuarioGateway;
import br.com.fiap.lunchtech.core.interfaces.ICardapioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.core.presenters.CardapioPresenter;
import br.com.fiap.lunchtech.core.usecases.cardapio.AlterarCardapioUseCase;
import br.com.fiap.lunchtech.core.usecases.cardapio.BuscarCardapioUseCase;
import br.com.fiap.lunchtech.core.usecases.cardapio.BuscarListaCardapioUseCase;
import br.com.fiap.lunchtech.core.usecases.cardapio.CadastrarCardapioUseCase;
import br.com.fiap.lunchtech.core.usecases.cardapio.DeletarCardapioUseCase;

import java.util.List;

public class CardapioController {
    private ICardapioDataSource cardapioDataSource;
    private IRestauranteDataSource restauranteDataSource;
    private IUsuarioDataSource usuarioDataSource;


    private CardapioController(ICardapioDataSource cardapioDataSource,
                               IRestauranteDataSource restauranteDataSource,
                               IUsuarioDataSource usuarioDataSource) {
        this.cardapioDataSource = cardapioDataSource;
        this.restauranteDataSource = restauranteDataSource;
        this.usuarioDataSource = usuarioDataSource;
    }

    public static CardapioController create (ICardapioDataSource cardapioDataSource,
                                             IRestauranteDataSource restauranteDataSource,
                                             IUsuarioDataSource usuarioDataSource) {
        return new CardapioController(cardapioDataSource, restauranteDataSource, usuarioDataSource);
    }

    public CardapioDTO cadastrarProdutoCardapio(NovoCardapioDTO novoCardapioDTO) {
        var cardapioGateway = CardapioGateway.create(cardapioDataSource);
        var usuarioGateway = UsuarioGateway.create(usuarioDataSource);
        var restauranteGateway = RestauranteGateway.create(restauranteDataSource, usuarioGateway);
        var cadastrarCardapioUseCase = CadastrarCardapioUseCase.create(cardapioGateway, restauranteGateway);

        var novoCardapio = cadastrarCardapioUseCase.run(novoCardapioDTO);
        var cardapioPresenter = CardapioPresenter.toDto(novoCardapio);

        return cardapioPresenter;
    }

    public CardapioDTO alterarProdutoCardapio(CardapioAlteradoDTO cardapioAlteradoDTO) {
        var usuarioGateway = UsuarioGateway.create(usuarioDataSource);
        var restauranteGateway = RestauranteGateway.create(restauranteDataSource, usuarioGateway);
        var cardapioGateway = CardapioGateway.create(cardapioDataSource, restauranteGateway);
        var alterarCardapioUseCase = AlterarCardapioUseCase.create(cardapioGateway, restauranteGateway);

        var cardapioAlterado = alterarCardapioUseCase.run(cardapioAlteradoDTO);
        var cardapioPresenter = CardapioPresenter.toDto(cardapioAlterado);

        return cardapioPresenter;
    }

    public void deletarProdutoCardapio(Long id) {
        var usuarioGateway = UsuarioGateway.create(usuarioDataSource);
        var restauranteGateway = RestauranteGateway.create(restauranteDataSource, usuarioGateway);
        var cardapioGateway = CardapioGateway.create(cardapioDataSource, restauranteGateway);
        var deletarCardapioUseCase = DeletarCardapioUseCase.create(cardapioGateway);

        deletarCardapioUseCase.run(id);
    }

    public CardapioDTO buscarProduto (CardapioBuscarDTO cardapioBuscarDTO) {
        var cardapioGateway = CardapioGateway.create(cardapioDataSource);
        var buscarCardapioUseCase = BuscarCardapioUseCase.create(cardapioGateway);

        var cardapioBuscado = buscarCardapioUseCase.run(cardapioBuscarDTO);
        var cardapioPresenter = CardapioPresenter.toDto(cardapioBuscado);

        return cardapioPresenter;
    }

    public List<CardapioDTO> buscarListaDoCardapio(String nomeRestaurante) {
        var usuarioGateway = UsuarioGateway.create(usuarioDataSource);
        var restauranteGateway = RestauranteGateway.create(restauranteDataSource, usuarioGateway);
        var cardapioGateway = CardapioGateway.create(cardapioDataSource, restauranteGateway);

        var buscarCardapioUseCase = BuscarListaCardapioUseCase.create(cardapioGateway);
        var listaCardapio = buscarCardapioUseCase.run(nomeRestaurante);

        return listaCardapio.stream().map(CardapioPresenter::toDto).toList();
    }
}
