package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.Usuario;

import java.util.List;

public interface IRestauranteGateway {
    Restaurante alterar(Restaurante restauranteAlteracao);

    Restaurante buscarPorNome(String nomeRestaurante);

    void deletar(String nomeRestaurante);

    Restaurante incluir(Restaurante restauranteAlteracao);

    List<Restaurante> buscarRestaurantesPorLoginDoUsuario(Usuario usuario);

    Restaurante buscarRestaurantePorId(Long id);

    List<Restaurante> buscarRestaurantes(Integer page, Integer size);
}
