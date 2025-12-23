package br.com.fiap.javacleanarch.core.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;

@Getter
@Setter
@EqualsAndHashCode
public class Estudante {

    private String identificacaoInterna;
    private String nome;
    private String enderecoEmail;
    private int idade;

    private static void idadeValida(int idade) {
        if (idade < 18) {
            throw new IllegalArgumentException("Idade inválida");
        }
    }

    private static void enderecoEmailValido(String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)) {
            throw new IllegalArgumentException("Endereço de e-mail inválido!");
        }
    }

    public static Estudante create (String nome, String enderecoEmail, int idade) throws IllegalArgumentException {
        if (nome.isEmpty() || enderecoEmail.isEmpty()) {
            throw new IllegalArgumentException("Dados inválidos!");
        }

        idadeValida(idade);
        enderecoEmailValido(enderecoEmail);

        Estudante estudante = new Estudante();
        estudante.setIdade(idade);
        estudante.setEnderecoEmail(enderecoEmail);
        estudante.setNome(nome);

        return estudante;
    }

    public static Estudante create (String identificacaoInterna, String nome, String enderecoEmail, int idade) throws IllegalArgumentException {
        if (nome.isEmpty() || enderecoEmail.isEmpty()) {
            throw new IllegalArgumentException("Dados inválidos!");
        }

        idadeValida(idade);
        enderecoEmailValido(enderecoEmail);

        Estudante estudante = new Estudante();
        estudante.setIdade(idade);
        estudante.setEnderecoEmail(enderecoEmail);
        estudante.setNome(nome);
        estudante.setIdentificacaoInterna(identificacaoInterna);

        return estudante;
    }

    public void setEnderecoEmail(String enderecoEmail) {
        enderecoEmailValido(enderecoEmail);
        this.enderecoEmail = enderecoEmail;
    }

    public void setIdade(int idade) {
        idadeValida(idade);
        this.idade = idade;
    }
}
