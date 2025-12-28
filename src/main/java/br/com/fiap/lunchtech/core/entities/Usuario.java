package br.com.fiap.lunchtech.core.entities;

import br.com.fiap.lunchtech.core.exceptions.UsuarioComInformacaoInvalidaException;
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
        validaLogin(login);

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEnderecoEmail(enderecoEmail);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setDataAtualizacao(LocalDateTime.now());
        usuario.setTipoDeUsuario(tipoDeUsuario);

        return  usuario;
    }

    public static Usuario create(String nomeUsuario,
                                 String enderecoEmail,
                                 String login,
                                 String tipoDeUsuario) {
        validaLogin(login);
        enderecoEmailValido(enderecoEmail);
        validaTipoUsuario(tipoDeUsuario);
        validaNomeUsuario(nomeUsuario);

        Usuario usuario = new Usuario();

        usuario.setNome(nomeUsuario);
        usuario.setEnderecoEmail(enderecoEmail);
        usuario.setLogin(login);
        usuario.setTipoDeUsuario(tipoDeUsuario);

        return usuario;
    }

    public static Usuario create(String login, String senha) {
        validaLogin(login);
        validaSenha(senha);

        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setSenha(senha);

        return usuario;
    }

    public void setEnderecoEmail(String enderecoEmail) {
        enderecoEmailValido(enderecoEmail);
        this.enderecoEmail = enderecoEmail;
    }

    public void setLogin(String login) {
        validaLogin(login);
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

    public void setNome(String nome) {
        validaNomeUsuario(nome);
        this.nome = nome;
    }

    private static void validaTipoUsuario(String tipoDeUsuario) {
        boolean isValid = "DONO_RESTAURANTE".equals(tipoDeUsuario)
                || "CLIENTE".equals(tipoDeUsuario);

        if (!isValid) {
            throw new UsuarioComInformacaoInvalidaException("Tipo de usuário inválido");
        }
    }

    private static void enderecoEmailValido(String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)) {
            throw new UsuarioComInformacaoInvalidaException("Endereço de e-mail inválido!");
        }
    }

    private static void validaSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new UsuarioComInformacaoInvalidaException("Senha não pode ser vazio ou nulo!");
        }
    }

    private static void validaLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new UsuarioComInformacaoInvalidaException("Login  não pode ser vazio ou nulo!");
        }
    }

    private static void validaNomeUsuario(String nomeUsuario) {
        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            throw new UsuarioComInformacaoInvalidaException("Nome de usuário não pode ser vazio ou nulo!");
        }
    }
}
