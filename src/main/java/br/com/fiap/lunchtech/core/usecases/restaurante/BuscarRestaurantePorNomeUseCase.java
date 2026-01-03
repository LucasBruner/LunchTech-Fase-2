package br.com.fiap.lunchtech.core.usecases.restaurante;

import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;

public class BuscarRestaurantePorNomeUseCase {
    private IRestauranteGateway restauranteGateway;

    private BuscarRestaurantePorNomeUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static BuscarRestaurantePorNomeUseCase create(IRestauranteGateway restauranteGateway) {
        return new BuscarRestaurantePorNomeUseCase(restauranteGateway);
    }

    public Restaurante run(String nomeRestaurante) {
        Restaurante restaurante = this.restauranteGateway.buscarPorNome(nomeRestaurante);

        if (restaurante == null) {
            throw new RestauranteNaoEncontradoException("O restaurante desejado não foi encontrado.");
        }

        return restaurante;
    }
}
