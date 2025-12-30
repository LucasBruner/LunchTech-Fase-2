package br.com.fiap.lunchtech.core.entities;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Endereco {
    private String logradouro;
    private int numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    private static final Pattern CEP_PATTERN = Pattern.compile("^\\d{8}$");

    public static Endereco create(String logradouro,
                                  int numero,
                                  String bairro,
                                  String cidade,
                                  String estado,
                                  String cep) {

        Endereco endereco = new Endereco();
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setCep(cep);

        return endereco;
    }

    private void setLogradouro(String logradouro) {
        validaLogradouro(logradouro);
        this.logradouro = logradouro;
    }

    private void validaLogradouro(String logradouro) {
        if(logradouro == null || logradouro.trim().isEmpty()) {
            throw new IllegalArgumentException("Logradouro inválido");
        }
    }

    private void setNumero(int numero) {
        validaNumero(numero);
        this.numero = numero;
    }

    private void validaNumero(int numero) {
        if(numero <= 0) {
            throw new IllegalArgumentException("Número inválido");
        }
    }

    private void setBairro(String bairro) {
        validaBairro(bairro);
        this.bairro = bairro;
    }

    private void validaBairro(String bairro) {
        if (bairro == null || bairro.trim().isEmpty()) {
            throw new IllegalArgumentException("Bairro inválido");
        }
    }

    private void setCidade(String cidade) {
        validaCidade(cidade);
        this.cidade = cidade;
    }

    private void validaCidade(String cidade) {
        if(cidade == null || cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Cidade inválida");
        }
    }

    private void setEstado(String estado) {
        validaEstado(estado);
        this.estado = estado;
    }

    private void validaEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("Estado inválido");
        }
    }

    private void setCep(String cep) {
        validaCep(cep);
        this.cep = cep;
    }

    private void validaCep(String cep) {
        if (cep == null
                || cep.trim().isEmpty()
                || !CEP_PATTERN.matcher(cep).matches()) {
            throw new IllegalArgumentException("CEP inválido");
        }
    }
}
