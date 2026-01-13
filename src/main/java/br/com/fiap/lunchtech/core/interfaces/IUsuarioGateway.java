package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.entities.Usuario;

import java.util.List;

public interface IUsuarioGateway {
    Usuario buscarPorLogin(String login);

    Usuario buscarPorLoginExistente(String login);

    Usuario incluir(Usuario novoUsuario);

    List<Usuario> buscarPorNome(String nomeUsuario);

    List<Usuario> buscarTodos();

    boolean buscarPorEmail(String emailUsuario);

    Usuario alterar(Usuario usuarioAlteracao);

    void deletar(String login);

    Usuario alterarSenha(Usuario usuarioAlteracao);

    Usuario buscarDadosLogin(String login);

    boolean buscarSeTipoUsuarioExistente(String tipo);
}
