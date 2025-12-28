package br.com.fiap.lunchtech.core.controllers;

import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.gateway.UsuarioGateway;
import br.com.fiap.lunchtech.core.interfaces.IDataSource;
import br.com.fiap.lunchtech.core.presenters.usuario.UsuarioPresenter;
import br.com.fiap.lunchtech.core.usecases.usuario.BuscarUsuariosPorNomeUseCase;
import br.com.fiap.lunchtech.core.usecases.usuario.CadastrarUsuarioUseCase;

import java.util.List;

public class UsuarioController {

    private final IDataSource dataStorageSource;

    private UsuarioController(IDataSource dataSource) {
        this.dataStorageSource = dataSource;
    }

    public static UsuarioController create(IDataSource dataSource) {
        return new UsuarioController(dataSource);
    }

    public UsuarioDTO cadastrar(NovoUsuarioDTO novoUsuarioDTO) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var useCaseNovoUsuario = CadastrarUsuarioUseCase.create(usuarioGateway);

        var usuario = useCaseNovoUsuario.run(novoUsuarioDTO);
        var usuarioPresenter = UsuarioPresenter.toDto(usuario);

        return usuarioPresenter;
    }

    public List<UsuarioDTO> buscarPorNome(String nomeUsuario) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var useCaseBuscarPorNome = BuscarUsuariosPorNomeUseCase.create(usuarioGateway);

        var usuarios = useCaseBuscarPorNome.run(nomeUsuario);
        List<UsuarioDTO> usuariosPresenter = usuarios.stream().map(UsuarioPresenter::toDto).toList();

        return usuariosPresenter;
    }
}
