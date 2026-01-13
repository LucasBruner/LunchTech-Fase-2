package br.com.fiap.lunchtech.core.presenters;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteCardapioDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Restaurante;

public class CardapioPresenter {

    public static CardapioDTO toDto(Cardapio cardapio) {
        RestauranteCardapioDTO restauranteCardapioDTO = toRestauranteCardapioDTO(cardapio.getRestaurante());
        return new CardapioDTO(cardapio.getId(),
                cardapio.getNomeProduto(),
                cardapio.getDescricao(),
                cardapio.getPreco(),
                cardapio.isApenasPresencial(),
                cardapio.getFotoPrato(),
                restauranteCardapioDTO);
    }

    private static RestauranteCardapioDTO toRestauranteCardapioDTO(Restaurante restaurante) {
        return new RestauranteCardapioDTO(restaurante.getNome(), null);
    }
}
