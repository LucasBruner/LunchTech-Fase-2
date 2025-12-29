package br.com.fiap.lunchtech.core.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Restaurante {
    private String nome;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private Endereco endereco;
    private Cardapio cardapio;
    private Usuario donoRestaurante;

    public static Restaurante create(String nome,
                                     String tipoCozinha,
                                     String horarioFuncionamento,
                                     Endereco endereco,
                                     Cardapio cardapio,
                                     Usuario donoRestaurante) {

        validaUsuario(donoRestaurante);

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(nome);
        restaurante.setTipoCozinha(tipoCozinha);
        restaurante.setHorarioFuncionamento(horarioFuncionamento);
        restaurante.setEndereco(endereco);
        restaurante.setCardapio(cardapio);
        restaurante.setDonoRestaurante(donoRestaurante);

        return null;
    }

    private static void validateNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
    }

    public void setNome(String nome) {
        validateNome(nome);
        this.nome = nome;
    }

    public void setDonoRestaurante(Usuario donoRestaurante) {
        validaUsuario(donoRestaurante);
        this.donoRestaurante = donoRestaurante;
    }

    private static void validaUsuario(Usuario donoRestaurante) {
        if ("CLIENTE".equals(donoRestaurante.getTipoDeUsuario().getTipoUsuario())) {
            throw new IllegalArgumentException("Um usuário cadastrado como cliente não pode ser atribuído como dono de restaurante");
        }
    }

}
