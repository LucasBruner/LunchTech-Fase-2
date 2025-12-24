package br.com.fiap.javacleanarch.core.interfaces;

import br.com.fiap.javacleanarch.core.entities.Usuario;

public interface IUsuarioGateway {
    Usuario buscarPorLogin(String login);

    Usuario incluir(Usuario novoUsuario);
}
