package br.com.fiap.lunchtech.core.controllers;

import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.gateway.RestauranteGateway;
import br.com.fiap.lunchtech.core.gateway.UsuarioGateway;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.core.presenters.RestaurantePresenter;
import br.com.fiap.lunchtech.core.usecases.restaurante.AlterarRestauranteUseCase;
import br.com.fiap.lunchtech.core.usecases.restaurante.BuscarRestaurantePorNomeUseCase;
import br.com.fiap.lunchtech.core.usecases.restaurante.CadastrarRestauranteUseCase;
import br.com.fiap.lunchtech.core.usecases.restaurante.DeletarRestauranteUseCase;

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

    public RestauranteDTO cadastrarRestaurante(NovoRestauranteDTO novoRestauranteDTO) {
        var restauranteGateway = RestauranteGateway.create(this.restauranteDataSource);
        var usuarioGateway = UsuarioGateway.create(this.usuarioDataSource);
        var useCaseNovoRestaurante = CadastrarRestauranteUseCase.create(restauranteGateway, usuarioGateway);

        var novoRestaurante = useCaseNovoRestaurante.run(novoRestauranteDTO);
        var restaurantePresenter = RestaurantePresenter.toDto(novoRestaurante);

        return restaurantePresenter;
    }

    public RestauranteDTO buscarRestaurantePorNome(String nomeRestaurante) {
        var restauranteGateway = RestauranteGateway.create(this.restauranteDataSource);
        var useCaseBuscarPorNome = BuscarRestaurantePorNomeUseCase.create(restauranteGateway);

        var restaurante = useCaseBuscarPorNome.run(nomeRestaurante);
        var restaurantePresenter = RestaurantePresenter.toDto(restaurante);

        return restaurantePresenter;
    }

    public RestauranteDTO alterarRestaurante(RestauranteAlteracaoDTO restauranteAlteracaoDTO) {
        var restauranteGateway = RestauranteGateway.create(this.restauranteDataSource);
        var usuarioGateway = UsuarioGateway.create(this.usuarioDataSource);
        var useCaseAlterarRestaurante = AlterarRestauranteUseCase.create(restauranteGateway, usuarioGateway);

        var restauranteAlterado = useCaseAlterarRestaurante.run(restauranteAlteracaoDTO);
        var restaurantePresenter = RestaurantePresenter.toDto(restauranteAlterado);

        return restaurantePresenter;
    }

    public void deletarRestaurante(String nomeRestaurante) {
        var restauranteGateway = RestauranteGateway.create(this.restauranteDataSource);
        var useCaseDeletarRestaurante = DeletarRestauranteUseCase.create(restauranteGateway);

        useCaseDeletarRestaurante.run(nomeRestaurante);
    }
}
