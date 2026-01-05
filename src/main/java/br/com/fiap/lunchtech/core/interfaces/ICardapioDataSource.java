package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioInfoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;

public interface ICardapioDataSource {
    CardapioInfoDTO incluirNovoProdutoCardapio(NovoCardapioDTO novoCardapioDTO);

    CardapioInfoDTO alterarProdutoCardapio(CardapioAlteradoDTO cardapioAlteradoDTO);

    void deletarProduto(String nomeProduto, String nomeRestaurante);

    CardapioDTO buscarProdutoPorNome(String nomeProduto, String nomeRestaurante);
}
