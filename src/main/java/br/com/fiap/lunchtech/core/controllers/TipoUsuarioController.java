package br.com.fiap.lunchtech.core.controllers;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.gateway.TipoUsuarioGateway;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.core.presenters.TipoUsuarioPresenter;
import br.com.fiap.lunchtech.core.usecases.tipousuario.*;

import java.util.Collections;
import java.util.List;

public class TipoUsuarioController {
    private final ITipoUsuarioDataSource tipoUsuarioDataSource;

    private TipoUsuarioController(ITipoUsuarioDataSource tipoUsuarioDataSource) {
        this.tipoUsuarioDataSource = tipoUsuarioDataSource;
    }

    public static TipoUsuarioController create (ITipoUsuarioDataSource tipoUsuarioDataSource) {
        return new TipoUsuarioController(tipoUsuarioDataSource);
    }

    public TipoUsuarioDTO cadastrarTipoUsuario(TipoUsuarioDTO tipoUsuarioDTO) {
        var tipoUsuarioGateway = TipoUsuarioGateway.create(tipoUsuarioDataSource);
        var cadastrarTipoUsuarioUseCase = CadastrarTipoUsuarioUseCase.create(tipoUsuarioGateway);

        var novoTipoUsuario = cadastrarTipoUsuarioUseCase.run(tipoUsuarioDTO);

        return TipoUsuarioPresenter.toDto(novoTipoUsuario);
    }

    public TipoUsuarioDTO alterarTipoUsuario(TipoUsuarioAlteracaoDTO tipoUsuarioDTO) {
        var tipoUsuarioGateway = TipoUsuarioGateway.create(tipoUsuarioDataSource);
        var alterarTipoUsuarioUseCase = AlterarTipoUsuarioUseCase.create(tipoUsuarioGateway);

        var tipoUsuarioAlterado = alterarTipoUsuarioUseCase.run(tipoUsuarioDTO);

        return TipoUsuarioPresenter.toDto(tipoUsuarioAlterado);
    }

    public void deletarTipoUsuario(TipoUsuarioDTO tipoUsuarioDTO) {
        var tipoUsuarioGateway = TipoUsuarioGateway.create(tipoUsuarioDataSource);
        var deletarTipoUsuarioUseCase = DeletarTipoUsuarioUseCase.create(tipoUsuarioGateway);

        deletarTipoUsuarioUseCase.run(tipoUsuarioDTO);
    }

    public List<TipoUsuarioDTO> buscarTipoUsuario(TipoUsuarioDTO tipoUsuarioDTO) {
        var tipoUsuarioGateway = TipoUsuarioGateway.create(tipoUsuarioDataSource);
        var buscarTipoUsuarioUseCase = BuscarTipoUsuarioUseCase.create(tipoUsuarioGateway);
        var buscarTipoUsuarioUseCaseTodos = BuscarTodosTipoUsuarioUseCase.create(tipoUsuarioGateway);


        if (tipoUsuarioDTO.tipoUsuario() == null || tipoUsuarioDTO.tipoUsuario().isEmpty()){
            var tipoUsuarioBuscado = buscarTipoUsuarioUseCaseTodos.run();
            return tipoUsuarioBuscado.stream().map(TipoUsuarioPresenter::toDto).toList();
        }
        var tipoUsuarioBuscado = buscarTipoUsuarioUseCase.run(tipoUsuarioDTO);
        return Collections.singletonList(TipoUsuarioPresenter.toDto(tipoUsuarioBuscado));
    }
}
