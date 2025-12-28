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
    private String email;
    private String login;
    private String senha;
    private LocalDateTime dataAtualizacao;
    private TipoUsuario tipoDeUsuario;
    private Endereco endereco;

    public static Usuario create(String nome,
                                 String email,
                                 String login,
                                 String senha,
                                 TipoUsuario tipoDeUsuario,
                                 Endereco enderecoUsuario) {

        enderecoEmailValido(email);
        validaSenha(senha);
        validaLogin(login);

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setDataAtualizacao(LocalDateTime.now());
        usuario.setTipoDeUsuario(tipoDeUsuario);
        usuario.setEndereco(enderecoUsuario);

        return  usuario;
    }

    public static Usuario create(String nomeUsuario,
                                 String enderecoEmail,
                                 String login,
                                 TipoUsuario tipoDeUsuario,
                                 Endereco enderecoUsuario) {
        validaLogin(login);
        enderecoEmailValido(enderecoEmail);
        validaNomeUsuario(nomeUsuario);

        Usuario usuario = new Usuario();

        usuario.setNome(nomeUsuario);
        usuario.setEmail(enderecoEmail);
        usuario.setLogin(login);
        usuario.setTipoDeUsuario(tipoDeUsuario);
        usuario.setEndereco(enderecoUsuario);

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

    public static Usuario create(String nomeUsuario,
                                 String email,
                                 String login,
                                 TipoUsuario tipoUsuario) {
        validaLogin(login);
        enderecoEmailValido(email);
        validaNomeUsuario(nomeUsuario);

        Usuario usuario = new Usuario();
        usuario.setNome(nomeUsuario);
        usuario.setEmail(email);
        usuario.setLogin(login);
        usuario.setTipoDeUsuario(tipoUsuario);

        return usuario;
    }

    public static Usuario create(String nomeUsuario,
                                 String enderecoEmail,
                                 String login,
                                 String senha,
                                 TipoUsuario tipoUsuario) {
        validaLogin(login);
        enderecoEmailValido(enderecoEmail);
        validaSenha(senha);
        validaNomeUsuario(nomeUsuario);

        Usuario usuario = new Usuario();
        usuario.setNome(nomeUsuario);
        usuario.setEmail(enderecoEmail);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuario.setTipoDeUsuario(tipoUsuario);

        return usuario;
    }

    public void setEmail(String email) {
        enderecoEmailValido(email);
        this.email = email;
    }

    public void setLogin(String login) {
        validaLogin(login);
        this.login = login;
    }

    public void setSenha(String senha) {
        validaSenha(senha);
        this.senha = senha;
    }

    public void setNome(String nome) {
        validaNomeUsuario(nome);
        this.nome = nome;
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
