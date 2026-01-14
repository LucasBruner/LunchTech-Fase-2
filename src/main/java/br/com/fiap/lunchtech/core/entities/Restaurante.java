package br.com.fiap.lunchtech.core.entities;

import java.util.Date;

public class Restaurante {
    private Long id;
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

    private static void validateTipoCozinha(String tipoCozinha) {
        if (tipoCozinha == null || tipoCozinha.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de cozinha inválido");
        }
    }

    public static Restaurante create(Long id,
                                     String nomeRestaurante,
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
        restaurante.setId(id);

        return restaurante;
    }

    public static Restaurante create(String nomeRestaurante) {
        Restaurante restaurante = new Restaurante();

        restaurante.setNome(nomeRestaurante);

        return restaurante;
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

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        validaId(id);
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public Date getHorarioFuncionamentoInicio() {
        return horarioFuncionamentoInicio;
    }

    public Date getHorarioFuncionamentoFim() {
        return horarioFuncionamentoFim;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public Usuario getDonoRestaurante() {
        return donoRestaurante;
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
        if (donoRestaurante == null) {
            throw new IllegalArgumentException("Dono do restaurante não pode ser nulo");
        }
        if ("CLIENTE".equals(donoRestaurante.getTipoDeUsuario().getTipoUsuario())) {
            throw new IllegalArgumentException("Um usuário cadastrado como cliente não pode ser atribuído como dono de restaurante");
        }
    }

    private void setTipoCozinha(String tipoCozinha) {
        validateTipoCozinha(tipoCozinha);
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

    private static void validaId(Long id){
        if (id <= 0) {
            throw new IllegalArgumentException("Não é possível utilizar o identificador.");
        }
    }
}
