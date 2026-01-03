package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

import java.util.List;

public class BuscarUsuariosPorNomeUseCase {
    private IUsuarioGateway usuarioGateway;

    private BuscarUsuariosPorNomeUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static BuscarUsuariosPorNomeUseCase create(IUsuarioGateway usuarioGateway) {
        return new BuscarUsuariosPorNomeUseCase(usuarioGateway);
    }

    public List<Usuario> run(String nome) {
        List<Usuario> usuarios = this.usuarioGateway.buscarPorNome(nome);

        if(usuarios.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Não foram encontrados usuários com esse nome.");
        }

        return usuarios;
    }
}
