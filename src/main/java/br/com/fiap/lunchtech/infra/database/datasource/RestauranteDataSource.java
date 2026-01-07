package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.infra.database.repositories.IRestauranteRepository;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDataSource implements IRestauranteDataSource {
    private IRestauranteRepository restauranteRepository;

    public RestauranteDataSource(IRestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public RestauranteDTO buscarRestaurantePorNome(String nomeRestaurante) {
        return null;
    }

    @Override
    public void deletarRestaurante(String nomeRestaurante) {

    }

    @Override
    public RestauranteDTO alterarRestaurante(RestauranteAlteracaoDTO restauranteAlteracaoDTO) {
        return null;
    }

    @Override
    public RestauranteDTO incluirNovoRestaurante(NovoRestauranteDTO novoRestauranteDTO) {
        return null;
    }
}
