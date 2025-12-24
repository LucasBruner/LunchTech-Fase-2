package br.com.fiap.javacleanarch.core.usecases;

import br.com.fiap.javacleanarch.core.entities.Usuario;
import br.com.fiap.javacleanarch.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.javacleanarch.core.interfaces.IUsuarioGateway;

public class BuscarUsuarioPorLogin {
    private IUsuarioGateway usuarioGateway;

    private BuscarUsuarioPorLogin(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static BuscarUsuarioPorLogin create(IUsuarioGateway usuarioGateway) {
        return new BuscarUsuarioPorLogin(usuarioGateway);
    }

    public Usuario run(String login) {
        Usuario usuario = this.usuarioGateway.buscarPorLogin(login);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        return usuario;
    }
}
