package br.com.fiap.lunchtech.core.controllers;

import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.gateway.UsuarioGateway;
import br.com.fiap.lunchtech.core.interfaces.IDataSource;
import br.com.fiap.lunchtech.core.presenters.UsuarioPresenter;
import br.com.fiap.lunchtech.core.usecases.usuario.AlterarSenhaUseCase;
import br.com.fiap.lunchtech.core.usecases.usuario.AlterarUsuarioUseCase;
import br.com.fiap.lunchtech.core.usecases.usuario.BuscarUsuariosPorNomeUseCase;
import br.com.fiap.lunchtech.core.usecases.usuario.CadastrarUsuarioUseCase;
import br.com.fiap.lunchtech.core.usecases.usuario.DeletarUsuarioUseCase;
import br.com.fiap.lunchtech.core.usecases.usuario.ValidarLoginUseCase;

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

    public UsuarioDTO alterarUsuario(UsuarioAlteracaoDTO usuarioAlteracaoDTO) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var useCaseAlterarUsuario = AlterarUsuarioUseCase.create(usuarioGateway);

        var usuario = useCaseAlterarUsuario.run(usuarioAlteracaoDTO);
        var usuarioPresenter = UsuarioPresenter.toDto(usuario);

        return usuarioPresenter;
    }

    public void deletarUsuario(String login) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var useCaseDeletarUsuario = DeletarUsuarioUseCase.create(usuarioGateway);

        useCaseDeletarUsuario.run(login);
    }

    public UsuarioAlteradoDTO alterarSenhaUsuario(UsuarioSenhaDTO usuarioSenhaDTO) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var useCaseAlterarSenhaUsuario = AlterarSenhaUseCase.create(usuarioGateway);

        var usuario = useCaseAlterarSenhaUsuario.run(usuarioSenhaDTO);
        var usuarioPresenter = UsuarioPresenter.mostrarUsuarioAlterado(usuario);

        return usuarioPresenter;
    }

    public boolean validarLoginUsuario(UsuarioSenhaDTO usuarioSenhaDTO) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var useCaseValidarLogin = ValidarLoginUseCase.create(usuarioGateway);

        return useCaseValidarLogin.run(usuarioSenhaDTO);
    }
}
