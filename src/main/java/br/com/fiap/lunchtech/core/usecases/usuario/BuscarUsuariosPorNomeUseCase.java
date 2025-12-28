package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

import java.util.List;

public class BuscarUsuariosPorNomeUseCase {
    private IUsuarioGateway usuarioGateway;

    public BuscarUsuariosPorNomeUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static BuscarUsuariosPorNomeUseCase create(IUsuarioGateway usuarioGateway) {
        return new BuscarUsuariosPorNomeUseCase(usuarioGateway);
    }

    public List<Usuario> run(String nome) {
        List<Usuario> usuarios = this.usuarioGateway.buscarPorNome(nome);
        return usuarios;
    }
}
