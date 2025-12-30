package br.com.fiap.lunchtech.core.dto.endereco;

public record EnderecoDTO(String logradouro,
                          int numero,
                          String bairro,
                          String cidade,
                          String estado,
                          String cep) {
}
