package br.com.fiap.javacleanarch.core.entities;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDateTime;

@Getter
@Setter
public class Usuario {
    private String nome;
    private String enderecoEmail;
    private String login;
    private String senha;
    private LocalDateTime dataAtualizacao;
    private String tipoDeUsuario;

    public static Usuario create(String nome,
                                 String enderecoEmail,
                                 String login,
                                 String senha,
                                 String tipoDeUsuario) {

        validaTipoUsuario(tipoDeUsuario);
        enderecoEmailValido(enderecoEmail);
        validaSenha(senha);
        validaUsuario(login);

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEnderecoEmail(enderecoEmail);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setDataAtualizacao(LocalDateTime.now());
        usuario.setTipoDeUsuario(tipoDeUsuario);

        return  usuario;
    }

    public void setEnderecoEmail(String enderecoEmail) {
        enderecoEmailValido(enderecoEmail);
        this.enderecoEmail = enderecoEmail;
    }

    public void setLogin(String login) {
        validaUsuario(login);
        this.login = login;
    }

    public void setSenha(String senha) {
        validaSenha(senha);
        this.senha = senha;
    }

    public void setTipoDeUsuario(String tipoDeUsuario) {
        validaTipoUsuario(tipoDeUsuario);
        this.tipoDeUsuario = tipoDeUsuario;
    }

    private static void validaTipoUsuario(String tipoDeUsuario) {
        boolean isValid = "DONO_RESTAURANTE".equals(tipoDeUsuario)
                || "CLIENTE".equals(tipoDeUsuario);

        if (!isValid) {
            throw new IllegalArgumentException("Tipo de usuário inválido");
        }
    }

    private static void enderecoEmailValido(String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)) {
            throw new IllegalArgumentException("Endereço de e-mail inválido!");
        }
    }

    private static void validaSenha(String senha) {
        if(senha.isEmpty()) {
            throw new IllegalArgumentException("Senha inválida");
        }
    }

    private static void validaUsuario(String login) {
        if(login.isEmpty()) {
            throw new IllegalArgumentException("Login inválido");
        }
    }
}
