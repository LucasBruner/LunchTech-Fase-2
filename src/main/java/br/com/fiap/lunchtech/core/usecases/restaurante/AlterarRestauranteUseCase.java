package br.com.fiap.lunchtech.core.usecases.restaurante;

import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;

public class AlterarRestauranteUseCase {
    private final IRestauranteGateway restauranteGateway;

    private AlterarRestauranteUseCase(IRestauranteGateway restauranteGateway) {
        this.restauranteGateway = restauranteGateway;
    }

    public static AlterarRestauranteUseCase create(IRestauranteGateway restauranteGateway) {
        return new AlterarRestauranteUseCase(restauranteGateway);
    }

    public Restaurante run() {

    }
}
