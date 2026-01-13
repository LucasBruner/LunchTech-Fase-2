package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioInfoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;

import java.util.List;

public interface ICardapioDataSource {
    CardapioInfoDTO incluirNovoProdutoCardapio(NovoCardapioDTO novoCardapioDTO);

    CardapioInfoDTO alterarProdutoCardapio(CardapioAlteradoDTO cardapioAlteradoDTO);

    void deletarProduto(Long id);

    CardapioDTO buscarProdutoPorNome(String nomeProduto, String nomeRestaurante);

    CardapioDTO buscarProdutoPorId(Long id);

    List<CardapioDTO> buscarProdutoPorIdRestaurante(Long restauranteId);
}
