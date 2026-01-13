package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

import java.util.List;

public class BuscarTodosUsuariosUseCase {
    private final IUsuarioGateway usuarioGateway;

    private BuscarTodosUsuariosUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static BuscarTodosUsuariosUseCase create(IUsuarioGateway usuarioGateway) {
        return new BuscarTodosUsuariosUseCase(usuarioGateway);
    }

    public List<Usuario> run() {
        List<Usuario> usuarios = this.usuarioGateway.buscarTodos();

        if(usuarios.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Não foram encontrados usuários.");
        }

        return usuarios;
    }
}
