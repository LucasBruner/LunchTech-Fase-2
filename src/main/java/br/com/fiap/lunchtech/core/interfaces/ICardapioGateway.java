package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.entities.Cardapio;

import java.util.List;

public interface ICardapioGateway {
    Cardapio buscarProdutoPorNome(String nomeProduto, String nomeRestaurante);

    Cardapio incluir(Cardapio cardapioAlteracao);

    Cardapio alterar(Cardapio cardapio);

    void deletar(Long id);

    Cardapio buscarProdutoPorId(Long id);

    List<Cardapio> buscarProdutosPorRestaurante(String restaurante);
}
