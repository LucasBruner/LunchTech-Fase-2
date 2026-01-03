package br.com.fiap.lunchtech.core.dto.cardapio;

import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;

public record CardapioDTO(String nomeProduto,
                          String descricao,
                          double preco,
                          boolean apenasPresencial,
                          String fotoPrato,
                          RestauranteDTO restaurante) {
}
