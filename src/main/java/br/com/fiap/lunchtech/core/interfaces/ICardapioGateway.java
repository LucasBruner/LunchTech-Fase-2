package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.entities.Cardapio;

public interface ICardapioGateway {
    Cardapio buscarProdutoPorNome(String nomeProduto, String nomeRestaurante);

    Cardapio incluir(Cardapio cardapioAlteracao);

    Cardapio alterar(Cardapio cardapio);

    void deletar(String nomeProduto, String nomeRestaurante);
}
