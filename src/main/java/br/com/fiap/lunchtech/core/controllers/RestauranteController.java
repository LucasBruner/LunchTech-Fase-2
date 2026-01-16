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
import br.com.fiap.lunchtech.core.usecases.restaurante.BuscarListaRestaurantesUseCase;
import br.com.fiap.lunchtech.core.usecases.restaurante.BuscarRestaurantePorNomeUseCase;
import br.com.fiap.lunchtech.core.usecases.restaurante.CadastrarRestauranteUseCase;
import br.com.fiap.lunchtech.core.usecases.restaurante.DeletarRestauranteUseCase;

import java.util.List;

public class RestauranteController {

    private IUsuarioDataSource usuarioDataSource;
    private final IRestauranteDataSource restauranteDataSource;

    private RestauranteController(IUsuarioDataSource usuarioDataSource,
                                  IRestauranteDataSource restauranteDataSource) {
        this.usuarioDataSource = usuarioDataSource;
        this.restauranteDataSource = restauranteDataSource;
    }

    public static RestauranteController create(IRestauranteDataSource restauranteDataSource,
                                               IUsuarioDataSource usuarioDataSource) {
        return new RestauranteController(usuarioDataSource, restauranteDataSource);
    }

    public RestauranteDTO cadastrarRestaurante(NovoRestauranteDTO novoRestauranteDTO) {
        var usuarioGateway = UsuarioGateway.create(this.usuarioDataSource);
        var restauranteGateway = RestauranteGateway.create(this.restauranteDataSource, usuarioGateway);
        var useCaseNovoRestaurante = CadastrarRestauranteUseCase.create(restauranteGateway, usuarioGateway);

        var novoRestaurante = useCaseNovoRestaurante.run(novoRestauranteDTO);
        return RestaurantePresenter.toDto(novoRestaurante);
    }

    public RestauranteDTO buscarRestaurantePorNome(String nomeRestaurante) {
        var usuarioGateway = UsuarioGateway.create(this.usuarioDataSource);
        var restauranteGateway = RestauranteGateway.create(this.restauranteDataSource, usuarioGateway);
        var useCaseBuscarPorNome = BuscarRestaurantePorNomeUseCase.create(restauranteGateway);

        var restaurante = useCaseBuscarPorNome.run(nomeRestaurante);
        return RestaurantePresenter.toDto(restaurante);
    }

    public RestauranteDTO alterarRestaurante(RestauranteAlteracaoDTO restauranteAlteracaoDTO) {
        var usuarioGateway = UsuarioGateway.create(this.usuarioDataSource);
        var restauranteGateway = RestauranteGateway.create(this.restauranteDataSource, usuarioGateway);
        var useCaseAlterarRestaurante = AlterarRestauranteUseCase.create(restauranteGateway, usuarioGateway);

        var restauranteAlterado = useCaseAlterarRestaurante.run(restauranteAlteracaoDTO);
        return RestaurantePresenter.toDto(restauranteAlterado);
    }

    public void deletarRestaurante(String nomeRestaurante) {
        var usuarioGateway = UsuarioGateway.create(usuarioDataSource);
        var restauranteGateway = RestauranteGateway.create(this.restauranteDataSource, usuarioGateway);
        var useCaseDeletarRestaurante = DeletarRestauranteUseCase.create(restauranteGateway);

        useCaseDeletarRestaurante.run(nomeRestaurante);
    }

    public List<RestauranteDTO> buscarListaRestaurantes(Integer page,
                                                        Integer size) {
        var usuarioGateway = UsuarioGateway.create(usuarioDataSource);
        var restauranteGateway = RestauranteGateway.create(restauranteDataSource, usuarioGateway);
        var useCaseBuscarListaRestaurantes = BuscarListaRestaurantesUseCase.create(restauranteGateway);

        var listaRestaurantes = useCaseBuscarListaRestaurantes.run(page, size);
        return listaRestaurantes.stream()
                .map(RestaurantePresenter::toDto)
                .toList();
    }
}
