package br.com.fiap.lunchtech.core.usecases.restaurante;

import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;

import java.util.List;
import java.util.Objects;

public class BuscarListaRestaurantesUseCase {
    private IRestauranteGateway restauranteGateway;

    private BuscarListaRestaurantesUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static BuscarListaRestaurantesUseCase create(IRestauranteGateway restauranteGateway) {
        return new BuscarListaRestaurantesUseCase(restauranteGateway);
    }

    public List<Restaurante> run(Integer page, Integer size) {
        int pageNumber = Objects.requireNonNullElse(page, 0);
        int pageSize = Objects.requireNonNullElse(size, 10);

        List<Restaurante> restaurantes = this.restauranteGateway.buscarRestaurantes(pageNumber, pageSize);

        if (restaurantes == null || restaurantes.isEmpty()) {
            throw new RestauranteNaoEncontradoException("Nenhum restaurante encontrado para os critérios informados.");
        }

        return restaurantes;
    }
}
