package br.com.fiap.lunchtech.core.gateway;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioInfoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteCardapioDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioDataSource;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;

public class CardapioGateway implements ICardapioGateway {
    ICardapioDataSource cardapioDataSource;

    private CardapioGateway(ICardapioDataSource cardapioDataSource) {
        this.cardapioDataSource = cardapioDataSource;
    }

    public static CardapioGateway create(ICardapioDataSource cardapioDataSource) {
        return new CardapioGateway(cardapioDataSource);
    }

    @Override
    public Cardapio buscarProdutoPorNome(String nomeProduto, String nomeRestaurante) {
        CardapioDTO cardapioDTO = cardapioDataSource.buscarProdutoPorNome(nomeProduto, nomeRestaurante);

        if(cardapioDTO == null) {
            throw new CardapioNaoExisteException("Produto não encontrado.");
        }

        return Cardapio.create(cardapioDTO.nomeProduto(),
                cardapioDTO.descricao(),
                cardapioDTO.preco(),
                cardapioDTO.apenasPresencial(),
                cardapioDTO.fotoPrato());
    }

    @Override
    public Cardapio incluir(Cardapio cardapioAlteracao) {
        RestauranteCardapioDTO restauranteDTO = mapearValoresRestauranteParaDTO(cardapioAlteracao.getRestaurante());
        NovoCardapioDTO novoCardapioDTO = new NovoCardapioDTO(
                cardapioAlteracao.getNomeProduto(),
                cardapioAlteracao.getDescricao(),
                cardapioAlteracao.getPreco(),
                cardapioAlteracao.isApenasPresencial(),
                cardapioAlteracao.getFotoPrato(),
                restauranteDTO);

        CardapioInfoDTO cardapioCriado = cardapioDataSource.incluirNovoProdutoCardapio(novoCardapioDTO);

        return Cardapio.create(cardapioCriado.nomeProduto(),
                cardapioCriado.descricao(),
                cardapioCriado.preco(),
                cardapioAlteracao.isApenasPresencial(),
                cardapioCriado.fotoPrato(),
                Restaurante.create(cardapioCriado.nomeProduto()));
    }

    @Override
    public Cardapio alterar(Cardapio cardapio) {
        RestauranteCardapioDTO restauranteCardapioDTO = mapearValoresRestauranteParaDTO(cardapio.getRestaurante());
        CardapioAlteradoDTO cardapioAlteradoDTO = new CardapioAlteradoDTO(cardapio.getNomeProduto(),
                cardapio.getDescricao(),
                cardapio.getPreco(),
                cardapio.isApenasPresencial(),
                cardapio.getFotoPrato(),
                restauranteCardapioDTO);

        CardapioInfoDTO cardapioAlterado = cardapioDataSource.alterarProdutoCardapio(cardapioAlteradoDTO);

        return Cardapio.create(cardapioAlterado.nomeProduto(),
                cardapioAlterado.descricao(),
                cardapioAlterado.preco(),
                cardapioAlterado.apenasPresencial(),
                cardapioAlterado.fotoPrato(),
                Restaurante.create(cardapioAlterado.nomeProduto()));
    }

    @Override
    public void deletar(String nomeProduto, String nomeRestaurante) {
        cardapioDataSource.deletarProduto(nomeProduto, nomeRestaurante);
    }

    private RestauranteCardapioDTO mapearValoresRestauranteParaDTO(Restaurante restaurante) {
        return new RestauranteCardapioDTO(restaurante.getNome());
    }
}
