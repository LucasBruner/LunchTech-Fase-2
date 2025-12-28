package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioJaExisteException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

public class AlterarSenhaUseCase {
    IUsuarioGateway usuarioGateway;

    private AlterarSenhaUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static AlterarSenhaUseCase create (IUsuarioGateway usuarioGateway) {
        return new AlterarSenhaUseCase(usuarioGateway);
    }

    public Usuario run (UsuarioSenhaDTO usuarioSenhaDTO) {
        Usuario usuarioExistente = usuarioGateway.buscarPorLogin(usuarioSenhaDTO.login());

        if (usuarioExistente == null) {
            throw new UsuarioJaExisteException("Usuário não possuí cadastro!");
        }

        Usuario usuarioAlteracao = Usuario.create(usuarioSenhaDTO.login(),
                usuarioSenhaDTO.senha());

        Usuario usuario = usuarioGateway.alterarSenha(usuarioAlteracao);

        return usuario;
    }
}
