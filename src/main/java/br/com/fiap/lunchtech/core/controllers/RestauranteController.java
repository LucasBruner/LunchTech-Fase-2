package br.com.fiap.lunchtech.core.controllers;

import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.gateway.RestauranteGateway;
import br.com.fiap.lunchtech.core.gateway.UsuarioGateway;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.core.presenters.RestaurantePresenter;
import br.com.fiap.lunchtech.core.usecases.restaurante.CadastrarRestauranteUseCase;

public class RestauranteController {

    private IUsuarioDataSource usuarioDataSource;
    private IRestauranteDataSource restauranteDataSource;

    private RestauranteController(IUsuarioDataSource usuarioDataSource, IRestauranteDataSource restauranteDataSource) {
        this.usuarioDataSource = usuarioDataSource;
        this.restauranteDataSource = restauranteDataSource;
    }

    public RestauranteController create(IUsuarioDataSource usuarioDataSource,
                                        IRestauranteDataSource restauranteDataSource) {
        return new RestauranteController(usuarioDataSource, restauranteDataSource);
    }

    public RestauranteDTO cadastrar(NovoRestauranteDTO novoRestauranteDTO) {
        var restauranteGateway = RestauranteGateway.create(this.restauranteDataSource);
        var usuarioGateway = UsuarioGateway.create(this.usuarioDataSource);
        var useCaseNovoRestaurante = CadastrarRestauranteUseCase.create(restauranteGateway, usuarioGateway);

        var novoRestaurante = useCaseNovoRestaurante.run(novoRestauranteDTO);
        var restaurantePresenter = RestaurantePresenter.toDto(novoRestaurante);

        return restaurantePresenter;
    }
}
