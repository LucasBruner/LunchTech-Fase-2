package br.com.fiap.lunchtech.core.usecases.restaurante;

import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.RestauranteJaExistenteException;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;

public class DeletarRestauranteUseCase {
    private final IRestauranteGateway restauranteGateway;

    private DeletarRestauranteUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static DeletarRestauranteUseCase create(IRestauranteGateway restauranteGateway) {
        return new DeletarRestauranteUseCase(restauranteGateway);
    }

    public void run(String nomeRestaurante) {
        Restaurante restauranteExistente = restauranteGateway.buscarPorNome(nomeRestaurante);

        if (restauranteExistente == null) {
            throw new RestauranteNaoEncontradoException("Resturante não foi encontrado.");
        }

        restauranteGateway.deletar(nomeRestaurante);
    }
}
