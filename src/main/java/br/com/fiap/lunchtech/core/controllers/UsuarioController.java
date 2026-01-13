package br.com.fiap.lunchtech.core.controllers;

import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.gateway.RestauranteGateway;
import br.com.fiap.lunchtech.core.gateway.UsuarioGateway;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.core.presenters.UsuarioPresenter;
import br.com.fiap.lunchtech.core.usecases.usuario.*;

import java.util.List;

public class UsuarioController {

    private final IUsuarioDataSource dataStorageSource;
    private IRestauranteDataSource restauranteDataSource;

    private UsuarioController(IUsuarioDataSource dataSource) {
        this.dataStorageSource = dataSource;
    }

    private UsuarioController(IUsuarioDataSource dataSource, IRestauranteDataSource restauranteDataSource) {
        this.dataStorageSource = dataSource;
        this.restauranteDataSource = restauranteDataSource;
    }

    public static UsuarioController create(IUsuarioDataSource dataSource) {
        return new UsuarioController(dataSource);
    }

    public static UsuarioController create(IUsuarioDataSource dataSource,
                                           IRestauranteDataSource restauranteDataSource) {
        return new UsuarioController(dataSource, restauranteDataSource);
    }

    public UsuarioDTO cadastrar(NovoUsuarioDTO novoUsuarioDTO) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var useCaseNovoUsuario = CadastrarUsuarioUseCase.create(usuarioGateway);

        var usuario = useCaseNovoUsuario.run(novoUsuarioDTO);

        return UsuarioPresenter.toDto(usuario);
    }

    public List<UsuarioDTO> buscarPorNome(String nomeUsuario) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);

        var useCaseBuscarTodos = BuscarTodosUsuariosUseCase.create(usuarioGateway);
        var useCaseBuscarPorNome = BuscarUsuariosPorNomeUseCase.create(usuarioGateway);

        var usuarios = nomeUsuario == null || nomeUsuario.isEmpty()
                ? useCaseBuscarTodos.run()
                : useCaseBuscarPorNome.run(nomeUsuario);

        return usuarios.stream().map(UsuarioPresenter::toDto).toList();
    }

    public UsuarioDTO alterarUsuario(UsuarioAlteracaoDTO usuarioAlteracaoDTO) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var useCaseAlterarUsuario = AlterarUsuarioUseCase.create(usuarioGateway);

        var usuario = useCaseAlterarUsuario.run(usuarioAlteracaoDTO);

        return UsuarioPresenter.toDto(usuario);
    }

    public void deletarUsuario(String login) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var restauranteGateway = RestauranteGateway.create(this.restauranteDataSource);

        var useCaseDeletarUsuario = DeletarUsuarioUseCase.create(usuarioGateway, restauranteGateway);

        useCaseDeletarUsuario.run(login);
    }

    public UsuarioAlteradoDTO alterarSenhaUsuario(UsuarioSenhaDTO usuarioSenhaDTO) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var useCaseAlterarSenhaUsuario = AlterarSenhaUseCase.create(usuarioGateway);

        var usuario = useCaseAlterarSenhaUsuario.run(usuarioSenhaDTO);

        return UsuarioPresenter.mostrarUsuarioAlterado(usuario);
    }

    public boolean validarLoginUsuario(UsuarioSenhaDTO usuarioSenhaDTO) {
        var usuarioGateway = UsuarioGateway.create(this.dataStorageSource);
        var useCaseValidarLogin = ValidarLoginUseCase.create(usuarioGateway);

        return useCaseValidarLogin.run(usuarioSenhaDTO);
    }
}
