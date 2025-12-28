package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

public class DeletarUsuarioUseCase {
    IUsuarioGateway usuarioGateway;

    private DeletarUsuarioUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static DeletarUsuarioUseCase create (IUsuarioGateway usuarioGateway) {
        return new DeletarUsuarioUseCase(usuarioGateway);
    }

    public void run (String login) {
        Usuario usuarioExiste = usuarioGateway.buscarPorLogin(login);

        if (usuarioExiste == null) {
           throw new IllegalArgumentException("Usuário não existe.");
        }

        usuarioGateway.deletar(login);
    }
}
