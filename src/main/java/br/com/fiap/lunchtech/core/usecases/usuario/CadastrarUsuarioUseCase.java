package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioComEmailJaCadastradoException;
import br.com.fiap.lunchtech.core.exceptions.UsuarioJaExisteException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

public class CadastrarUsuarioUseCase {
    IUsuarioGateway usuarioGateway;

    private CadastrarUsuarioUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static CadastrarUsuarioUseCase create (IUsuarioGateway usuarioGateway) {
        return new CadastrarUsuarioUseCase(usuarioGateway);
    }

    public Usuario run (NovoUsuarioDTO novoUsuarioDTO) {
        Usuario usuarioExistente = usuarioGateway.buscarPorLogin(novoUsuarioDTO.login());

        if (usuarioExistente != null) {
            throw new UsuarioJaExisteException("Usuário já possuí registro na base!");
        }

        boolean emailJaCadastrado = usuarioGateway.buscarPorEmail(novoUsuarioDTO.enderecoEmail());

        if(emailJaCadastrado) {
            throw new UsuarioComEmailJaCadastradoException("Esse e-mail já está sendo utilizado por outro usuário.");
        }

        Usuario novoUsuario = Usuario.create(novoUsuarioDTO.nomeUsuario(),
        novoUsuarioDTO.enderecoEmail(),
        novoUsuarioDTO.login(),
        novoUsuarioDTO.senha(),
        TipoUsuario.create(novoUsuarioDTO.tipoDeUsuario()));

        Usuario usuario = usuarioGateway.incluir(novoUsuario);

        return usuario;
    }
}
