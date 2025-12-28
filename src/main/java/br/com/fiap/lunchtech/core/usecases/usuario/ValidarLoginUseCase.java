package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.LoginInvalidoException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

public class ValidarLoginUseCase {
    IUsuarioGateway usuarioGateway;

    private ValidarLoginUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static ValidarLoginUseCase create (IUsuarioGateway usuarioGateway) {
        return new ValidarLoginUseCase(usuarioGateway);
    }

    public void run(UsuarioSenhaDTO usuarioLogin) {
        Usuario usuarioValido = usuarioGateway.buscarDadosLogin(usuarioLogin.login());
        
        if (usuarioValido == null ||
                !usuarioLogin.login().equals(usuarioValido.getLogin()) ||
                !usuarioLogin.senha().equals(usuarioValido.getSenha())) {
            throw new LoginInvalidoException("Usuário ou senha incorretos");
        }
    }
}
