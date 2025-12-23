package br.com.fiap.javacleanarch.core.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Endereco {
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    private static Endereco create(String logradouro,
                                   String numero,
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
}
