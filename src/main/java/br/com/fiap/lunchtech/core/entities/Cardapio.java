package br.com.fiap.lunchtech.core.entities;

import lombok.Getter;

@Getter
public class Cardapio {
    private String nomeProduto;
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
        cardapio.setNomeProduto(nomeCardapio);
        cardapio.setDescricao(descricao);
        cardapio.setPreco(preco);
        cardapio.setApenasPresencial(apenasPresencial);
        cardapio.setFotoPrato(fotoPrato);
        cardapio.setRestaurante(restaurante);

        return cardapio;
    }

    private void setRestaurante(Restaurante restaurante) {
        validaRestaurante(restaurante);
        this.restaurante = restaurante;
    }

    private void setNomeProduto(String nomeProduto) {
        validaNomeProduto(nomeProduto);
        this.nomeProduto = nomeProduto;
    }

    private void setDescricao(String descricao) {
        validaDescricaoProduto(descricao);
        this.descricao = descricao;
    }

    private void setPreco(double preco) {
        validaPrecoProduto(preco);
        this.preco = preco;
    }

    private void setApenasPresencial(boolean apenasPresencial) {
        this.apenasPresencial = apenasPresencial;
    }

    private void setFotoPrato(String fotoPrato) {
        this.fotoPrato = fotoPrato;
    }

    private static void validaRestaurante(Restaurante restaurante) {
        if(restaurante.getNome().trim().isBlank()) {
            throw new IllegalArgumentException("Restaurante inválido");
        }
    }

    private static void validaNomeProduto(String nomeProduto) {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cardápio inválido");
        }
    }

    private static void validaPrecoProduto(double preco) {
        if (Double.isNaN(preco) || preco <= 0) {
            throw new IllegalArgumentException("Preço inválido");
        }
    }

    private static void validaDescricaoProduto(String descricao) {
        if(descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Forneça uma descrição para o produto");
        }
    }
}
