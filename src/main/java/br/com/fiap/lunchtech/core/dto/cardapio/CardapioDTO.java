package br.com.fiap.lunchtech.core.dto.cardapio;

public record CardapioDTO(String nomeProduto,
                          String descricao,
                          double preco,
                          boolean apenasPresencial,
                          String fotoPrato) {
}
