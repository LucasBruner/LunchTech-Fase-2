package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioComEmailJaCadastradoException;
import br.com.fiap.lunchtech.core.exceptions.UsuarioJaExisteException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

public class ValidarLoginUseCase {
    IUsuarioGateway usuarioGateway;

    private ValidarLoginUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static ValidarLoginUseCase create (IUsuarioGateway usuarioGateway) {
        return new ValidarLoginUseCase(usuarioGateway);
    }

    public void run(String Login) {

    }
}
