package br.com.fiap.javacleanarch.core.controllers;

import br.com.fiap.javacleanarch.core.dto.NovoUsuarioDTO;
import br.com.fiap.javacleanarch.core.dto.UsuarioDTO;
import br.com.fiap.javacleanarch.core.exceptions.UsuarioJaExisteException;
import br.com.fiap.javacleanarch.core.gateway.UsuarioGateway;
import br.com.fiap.javacleanarch.core.interfaces.IDataSource;
import br.com.fiap.javacleanarch.core.presenters.UsuarioPresenter;
import br.com.fiap.javacleanarch.core.usecases.CadastrarUsuarioUseCase;

public class UsuarioController {

    private final IDataSource dataStorageSource;

    private UsuarioController(IDataSource dataSource) {
        this.dataStorageSource = dataSource;
    }

    public static UsuarioController create(IDataSource dataSource) {
        return new UsuarioController(dataSource);
    }

    public UsuarioDTO create(NovoUsuarioDTO novoUsuarioDTO) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var useCaseNovoUsuario = CadastrarUsuarioUseCase.create(usuarioGateway);

        try {
            var usuario = useCaseNovoUsuario.run(novoUsuarioDTO);
            var usuarioPresenter = UsuarioPresenter.toDto(usuario);

            return usuarioPresenter;
        } catch (UsuarioJaExisteException e) {
            return null;
        }
    }
}
