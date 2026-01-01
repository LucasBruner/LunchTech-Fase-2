package br.com.fiap.lunchtech.core.entities;

import lombok.Getter;

import java.util.Date;

@Getter
public class Restaurante {
    private String nome;
    private String tipoCozinha;
    private Date horarioFuncionamentoInicio;
    private Date horarioFuncionamentoFim;
    private Endereco endereco;
    private Cardapio cardapio;
    private Usuario donoRestaurante;

    public static Restaurante create(String nome,
                                     String tipoCozinha,
                                     Date horarioFuncionamentoInicio,
                                     Date horarioFuncionamentoFim,
                                     Endereco endereco,
                                     Cardapio cardapio,
                                     Usuario donoRestaurante) {
        Restaurante restaurante = new Restaurante();

        restaurante.setNome(nome);
        restaurante.setTipoCozinha(tipoCozinha);
        restaurante.setHorarioFuncionamentoInicio(horarioFuncionamentoInicio);
        restaurante.setHorarioFuncionamentoFim(horarioFuncionamentoFim);
        restaurante.setEndereco(endereco);
        restaurante.setCardapio(cardapio);
        restaurante.setDonoRestaurante(donoRestaurante);

        return restaurante;
    }

    private static void validateNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
    }

    public static Restaurante create(String nomeRestaurante,
                                     String tipoCozinha,
                                     Date horarioFuncionamentoInicio,
                                     Date horarioFuncionamentoFim,
                                     Endereco enderecoRestaurante,
                                     Usuario donoRestaurante) {
        Restaurante restaurante = new Restaurante();

        restaurante.setNome(nomeRestaurante);
        restaurante.setTipoCozinha(tipoCozinha);
        restaurante.setHorarioFuncionamentoInicio(horarioFuncionamentoInicio);
        restaurante.setHorarioFuncionamentoFim(horarioFuncionamentoFim);
        restaurante.setEndereco(enderecoRestaurante);
        restaurante.setDonoRestaurante(donoRestaurante);

        return restaurante;
    }

    private void setNome(String nome) {
        validateNome(nome);
        this.nome = nome;
    }

    private void setDonoRestaurante(Usuario donoRestaurante) {
        validaUsuario(donoRestaurante);
        this.donoRestaurante = donoRestaurante;
    }

    private static void validaUsuario(Usuario donoRestaurante) {
        if ("CLIENTE".equals(donoRestaurante.getTipoDeUsuario().getTipoUsuario())) {
            throw new IllegalArgumentException("Um usuário cadastrado como cliente não pode ser atribuído como dono de restaurante");
        }
    }

    private void setTipoCozinha(String tipoCozinha) {
        this.tipoCozinha = tipoCozinha;
    }

    private void setHorarioFuncionamentoInicio(Date horarioFuncionamentoInicio) {
        this.horarioFuncionamentoInicio = horarioFuncionamentoInicio;
    }

    private void setHorarioFuncionamentoFim(Date horarioFuncionamentoFim) {
        this.horarioFuncionamentoFim = horarioFuncionamentoFim;
    }

    private void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    private void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }
}
