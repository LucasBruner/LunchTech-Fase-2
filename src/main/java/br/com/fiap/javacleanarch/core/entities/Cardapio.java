package br.com.fiap.javacleanarch.core.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cardapio {
    private String nomeCardapio;
    private String descricao;
    private double preco;
    private boolean apenasPresencial;
    private String fotoPrato;
    private Restaurante restaurante;

    public static Cardapio create(String nomeCardapio,
                                  String descricao,
                                  double preco,
                                  boolean apenasPresencial,
                                  String fotoPrato,
                                  Restaurante restaurante) {
        validaRestaurante(restaurante);

        Cardapio cardapio = new Cardapio();
        cardapio.setNomeCardapio(nomeCardapio);
        cardapio.setDescricao(descricao);
        cardapio.setPreco(preco);
        cardapio.setApenasPresencial(apenasPresencial);
        cardapio.setFotoPrato(fotoPrato);
        cardapio.setRestaurante(restaurante);

        return cardapio;
    }

    public void setRestaurante(Restaurante restaurante) {
        validaRestaurante(restaurante);
        this.restaurante = restaurante;
    }

    private static void validaRestaurante(Restaurante restaurante) {
        if(restaurante.getNome().trim().isBlank()) {
            throw new IllegalArgumentException("Restaurante inválido");
        }
    }
}
