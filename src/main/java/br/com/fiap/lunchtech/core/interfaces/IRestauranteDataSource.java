package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;

import java.util.List;

public interface IRestauranteDataSource {
    RestauranteDTO buscarRestaurantePorNome(String nomeRestaurante);

    void deletarRestaurante(String nomeRestaurante);

    RestauranteDTO alterarRestaurante(RestauranteAlteracaoDTO restauranteAlteracaoDTO);

    RestauranteDTO incluirNovoRestaurante(NovoRestauranteDTO novoRestauranteDTO);

    List<RestauranteDTO> buscarRestaurantesPorLogin(String login);
}
