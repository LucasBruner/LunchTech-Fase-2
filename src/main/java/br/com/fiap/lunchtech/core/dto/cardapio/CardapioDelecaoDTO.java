package br.com.fiap.lunchtech.core.dto.cardapio;

import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;

public record CardapioDelecaoDTO(String nomeProduto,
                                 RestauranteDTO restaurante) {
}
