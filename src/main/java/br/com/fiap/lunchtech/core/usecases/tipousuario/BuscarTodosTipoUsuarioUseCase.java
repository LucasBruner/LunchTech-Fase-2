package br.com.fiap.lunchtech.core.usecases.tipousuario;

import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;

import java.util.List;

public class BuscarTodosTipoUsuarioUseCase {
    private final ITipoUsuarioGateway tipoUsuarioGateway;

    private BuscarTodosTipoUsuarioUseCase(ITipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public static BuscarTodosTipoUsuarioUseCase create(ITipoUsuarioGateway tipoUsuarioGateway) {
        return new BuscarTodosTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    public List<TipoUsuario> run() {
        List<TipoUsuario> tipoUsuario = tipoUsuarioGateway.buscarTodosTipoUsuario();

        if(tipoUsuario == null) {
            throw new TipoUsuarioNaoExisteException("Tipo de usuário não encontrado.");
        }

        return tipoUsuario;
    }
}
