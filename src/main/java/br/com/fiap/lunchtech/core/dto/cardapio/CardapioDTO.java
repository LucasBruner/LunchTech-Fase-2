package br.com.fiap.lunchtech.core.dto.cardapio;

import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteCardapioDTO;

public record CardapioDTO(Long id,
                          String nomeProduto,
                          String descricao,
                          double preco,
                          boolean apenasPresencial,
                          String fotoPrato,
                          RestauranteCardapioDTO restauranteCardapioDTO) {
}
