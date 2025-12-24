package br.com.fiap.javacleanarch.core.usecases;

import br.com.fiap.javacleanarch.core.dto.NovoUsuarioDTO;
import br.com.fiap.javacleanarch.core.entities.Usuario;
import br.com.fiap.javacleanarch.core.exceptions.UsuarioJaExisteException;
import br.com.fiap.javacleanarch.core.interfaces.IUsuarioGateway;

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

        Usuario novoUsuario = Usuario.create(novoUsuarioDTO.nomeUsuario(),
        novoUsuarioDTO.enderecoEmail(),
        novoUsuarioDTO.login(),
        novoUsuarioDTO.senha(),
        novoUsuarioDTO.tipoDeUsuario());

        Usuario usuario = usuarioGateway.incluir(novoUsuario);

        return usuario;
    }
}
