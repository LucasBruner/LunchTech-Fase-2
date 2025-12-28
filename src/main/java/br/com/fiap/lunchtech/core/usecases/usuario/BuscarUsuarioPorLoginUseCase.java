package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

public class BuscarUsuarioPorLoginUseCase {
    private IUsuarioGateway usuarioGateway;

    private BuscarUsuarioPorLoginUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static BuscarUsuarioPorLoginUseCase create(IUsuarioGateway usuarioGateway) {
        return new BuscarUsuarioPorLoginUseCase(usuarioGateway);
    }

    public Usuario run(String login) {
        Usuario usuario = this.usuarioGateway.buscarPorLogin(login);

        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado");
        }

        return usuario;
    }
}
