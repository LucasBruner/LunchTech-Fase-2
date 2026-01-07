package br.com.fiap.lunchtech.core.usecases.tipousuario;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;

public class BuscarTipoUsuarioUseCase {
    private ITipoUsuarioGateway tipoUsuarioGateway;

    private BuscarTipoUsuarioUseCase(ITipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public static BuscarTipoUsuarioUseCase create(ITipoUsuarioGateway tipoUsuarioGateway) {
        return new BuscarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    public TipoUsuario run(TipoUsuarioDTO tipoUsuarioDTO) {
        TipoUsuario tipoUsuario = tipoUsuarioGateway.buscarTipoUsuarioPorNome(tipoUsuarioDTO.tipoUsuario());

        if(tipoUsuario == null) {
            throw new TipoUsuarioNaoExisteException("Tipo de usuário não encontrado.");
        }

        return tipoUsuario;
    }
}
