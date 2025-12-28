package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.entities.Usuario;

import java.util.List;

public interface IUsuarioGateway {
    Usuario buscarPorLogin(String login);

    Usuario incluir(Usuario novoUsuario);

    List<Usuario> buscarPorNome(String nomeUsuario);

    boolean buscarPorEmail(String emailUsuario);

    Usuario alterar(Usuario usuarioAlteracao);

    void deletar(String login);

    Usuario alterarSenha(Usuario usuarioAlteracao);
}
