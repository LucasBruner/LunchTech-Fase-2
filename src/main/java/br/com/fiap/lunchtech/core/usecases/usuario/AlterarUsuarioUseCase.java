package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioComEmailJaCadastradoException;
import br.com.fiap.lunchtech.core.exceptions.UsuarioJaExisteException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

public class AlterarUsuarioUseCase {
    IUsuarioGateway usuarioGateway;

    private AlterarUsuarioUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static AlterarUsuarioUseCase create (IUsuarioGateway usuarioGateway) {
        return new AlterarUsuarioUseCase(usuarioGateway);
    }

    public Usuario run (UsuarioAlteracaoDTO usuarioAlteracaoDTO) {
        Usuario usuarioExistente = usuarioGateway.buscarPorLogin(usuarioAlteracaoDTO.login());

        if (usuarioExistente != null) {
            throw new UsuarioJaExisteException("Usuário já possuí registro na base!");
        }

        boolean emailJaCadastrado = usuarioGateway.buscarPorEmail(usuarioAlteracaoDTO.enderecoEmail());

        if(emailJaCadastrado) {
            throw new UsuarioComEmailJaCadastradoException("Esse e-mail já está sendo utilizado por outro usuário.");
        }

        Usuario usuarioAlteracao = Usuario.create(usuarioAlteracaoDTO.nomeUsuario(),
                usuarioAlteracaoDTO.enderecoEmail(),
                usuarioAlteracaoDTO.login(),
                TipoUsuario.create(usuarioAlteracaoDTO.tipoDeUsuario()));

        Usuario usuario = usuarioGateway.alterar(usuarioAlteracao);

        return usuario;
    }
}
