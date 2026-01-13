package br.com.fiap.lunchtech.core.gateway;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioInfoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteCardapioDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.exceptions.CardapioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ICardapioDataSource;
import br.com.fiap.lunchtech.core.interfaces.ICardapioGateway;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;

import java.util.List;

public class CardapioGateway implements ICardapioGateway {
    ICardapioDataSource cardapioDataSource;
    IRestauranteGateway restauranteGateway;

    private CardapioGateway(ICardapioDataSource cardapioDataSource) {
        this.cardapioDataSource = cardapioDataSource;
    }

    private CardapioGateway(ICardapioDataSource cardapioDataSource,
                            IRestauranteGateway restauranteGateway) {
        this.cardapioDataSource = cardapioDataSource;
        this.restauranteGateway = restauranteGateway;
    }

    public static CardapioGateway create(ICardapioDataSource cardapioDataSource) {
        return new CardapioGateway(cardapioDataSource);
    }

    public static CardapioGateway create(ICardapioDataSource cardapioDataSource,
                                         IRestauranteGateway restauranteGateway) {
        return new CardapioGateway(cardapioDataSource, restauranteGateway);
    }

    @Override
    public Cardapio buscarProdutoPorNome(String nomeProduto, String nomeRestaurante) {
        CardapioDTO cardapioDTO = cardapioDataSource.buscarProdutoPorNome(nomeProduto, nomeRestaurante);

        if(cardapioDTO == null) {
            throw new CardapioNaoExisteException("Produto não encontrado.");
        }
        Restaurante restaurante = restauranteGateway.buscarRestaurantePorId(cardapioDTO.restauranteCardapioDTO().id());
        return Cardapio.create(cardapioDTO.id(),
                cardapioDTO.nomeProduto(),
                cardapioDTO.descricao(),
                cardapioDTO.preco(),
                cardapioDTO.apenasPresencial(),
                cardapioDTO.fotoPrato(),
                restaurante);
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
        CardapioAlteradoDTO cardapioAlteradoDTO = new CardapioAlteradoDTO(cardapio.getId(),
                cardapio.getNomeProduto(),
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
    public void deletar(Long id) {
        cardapioDataSource.deletarProduto(id);
    }

    @Override
    public Cardapio buscarProdutoPorId(Long id) {
        CardapioDTO cardapioDTO = cardapioDataSource.buscarProdutoPorId(id);

        if(cardapioDTO == null) {
            throw new CardapioNaoExisteException("Produto não encontrado.");
        }

        Restaurante restaurante = restauranteGateway.buscarRestaurantePorId(cardapioDTO.restauranteCardapioDTO().id());
        return createCardapio(cardapioDTO, restaurante);
    }

    @Override
    public List<Cardapio> buscarProdutosPorRestaurante(String nomeRestaurante) {
        Restaurante restaurante = restauranteGateway.buscarPorNome(nomeRestaurante);
        List<CardapioDTO> cardapioDTOList = cardapioDataSource.buscarProdutoPorIdRestaurante(restaurante.getId());

        if(cardapioDTOList.isEmpty()) {
            throw new CardapioNaoExisteException("Produtos não encontrados.");
        }

        return cardapioDTOList.stream()
                .map(c -> createCardapio(c, restaurante))
                .toList();
    }

    private RestauranteCardapioDTO mapearValoresRestauranteParaDTO(Restaurante restaurante) {
        return new RestauranteCardapioDTO(restaurante.getNome(), restaurante.getId());
    }

    private Cardapio createCardapio(CardapioDTO cardapioDTO, Restaurante restaurante) {
        return  Cardapio.create(cardapioDTO.id(),
                cardapioDTO.nomeProduto(),
                cardapioDTO.descricao(),
                cardapioDTO.preco(),
                cardapioDTO.apenasPresencial(),
                cardapioDTO.fotoPrato(),
                restaurante);
    }
}
