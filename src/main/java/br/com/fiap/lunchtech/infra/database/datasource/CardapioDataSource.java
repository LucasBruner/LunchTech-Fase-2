package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioInfoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteCardapioDTO;
import br.com.fiap.lunchtech.core.interfaces.ICardapioDataSource;
import br.com.fiap.lunchtech.infra.database.entities.CardapioEntity;
import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import br.com.fiap.lunchtech.infra.database.repositories.ICardapioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CardapioDataSource implements ICardapioDataSource {
    private final ICardapioRepository cardapioRepository;
    private final RestauranteDataSource restauranteDataSource;

    public CardapioDataSource(ICardapioRepository cardapioRepository,
                              RestauranteDataSource restauranteDataSource) {
        this.cardapioRepository = cardapioRepository;
        this.restauranteDataSource = restauranteDataSource;
    }

    @Override
    public CardapioInfoDTO incluirNovoProdutoCardapio(NovoCardapioDTO novoCardapioDTO) {
        CardapioEntity novoCardapio = new CardapioEntity();
        RestauranteEntity restaurante = restauranteDataSource.buscarRestauranteEntity(novoCardapioDTO.restaurante().nomeRestaurante());

        novoCardapio.setNome(novoCardapioDTO.nomeProduto());
        novoCardapio.setDescricao(novoCardapioDTO.descricao());
        novoCardapio.setPreco(novoCardapioDTO.preco());
        novoCardapio.setApenasPresencial(novoCardapioDTO.apenasPresencial());
        novoCardapio.setFotoPrato(novoCardapioDTO.fotoPrato());
        novoCardapio.setRestaurante(restaurante);

        cardapioRepository.save(novoCardapio);

        return mapCardapioEntityToCardapioInfoDTO(novoCardapio);
    }

    @Override
    public CardapioInfoDTO alterarProdutoCardapio(CardapioAlteradoDTO cardapioAlteradoDTO) {
        CardapioEntity novoCardapio = new CardapioEntity();
        RestauranteEntity restaurante = restauranteDataSource.buscarRestauranteEntity(cardapioAlteradoDTO.restaurante().nomeRestaurante());

        novoCardapio.setNome(cardapioAlteradoDTO.nomeProduto());
        novoCardapio.setDescricao(cardapioAlteradoDTO.descricao());
        novoCardapio.setPreco(cardapioAlteradoDTO.preco());
        novoCardapio.setApenasPresencial(cardapioAlteradoDTO.apenasPresencial());
        novoCardapio.setFotoPrato(cardapioAlteradoDTO.fotoPrato());
        novoCardapio.setRestaurante(restaurante);

        cardapioRepository.save(novoCardapio);

        return mapCardapioEntityToCardapioInfoDTO(novoCardapio);
    }

    @Override
    public void deletarProduto(String nomeProduto, String nomeRestaurante) {
        Long restauranteID = restauranteDataSource.buscarRestauranteID(nomeRestaurante);
        CardapioEntity itemCardapio = cardapioRepository.findByNome(nomeProduto, restauranteID);
        cardapioRepository.delete(itemCardapio);
    }

    @Override
    public CardapioDTO buscarProdutoPorNome(String nomeProduto, String nomeRestaurante) {
        try {
            Long restauranteID = restauranteDataSource.buscarRestauranteID(nomeRestaurante);
            CardapioEntity itemCardapio = cardapioRepository.findByNome(nomeProduto, restauranteID);
            return mapCardapioEntityToCardapioDTO(itemCardapio);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Produto não encontrado!", e);
        }
    }

    private CardapioDTO mapCardapioEntityToCardapioDTO(CardapioEntity cardapio) {
        return new CardapioDTO(cardapio.getNome(),
                cardapio.getDescricao(),
                cardapio.getPreco(),
                cardapio.isApenasPresencial(),
                cardapio.getFotoPrato());
    }


    private CardapioInfoDTO mapCardapioEntityToCardapioInfoDTO(CardapioEntity novoCardapio) {
        RestauranteEntity restaurante = novoCardapio.getRestaurante();
        RestauranteCardapioDTO restauranteCardapioDTO = new RestauranteCardapioDTO(restaurante.getNome());
        return new CardapioInfoDTO(novoCardapio.getNome(),
                novoCardapio.getDescricao(),
                novoCardapio.getPreco(),
                novoCardapio.isApenasPresencial(),
                novoCardapio.getFotoPrato(),
                restauranteCardapioDTO);
    }
}
