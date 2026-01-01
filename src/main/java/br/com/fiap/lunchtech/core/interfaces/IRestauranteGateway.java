package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.entities.Restaurante;

public interface IRestauranteGateway {
    Restaurante alterar(Restaurante restauranteAlteracao);

    Restaurante buscarPorNome(String nomeRestaurante);
}
