package br.com.fiap.lunchtech.core.usecases.tipousuario;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;

public class AlterarTipoUsuarioUseCase {
    private ITipoUsuarioGateway tipoUsuarioGateway;

    private AlterarTipoUsuarioUseCase(ITipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public static AlterarTipoUsuarioUseCase create(ITipoUsuarioGateway tipoUsuarioGateway) {
        return new AlterarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    public TipoUsuario run(TipoUsuarioDTO tipoUsuarioAlterado) {
        TipoUsuario tipoUsuarioExistente = tipoUsuarioGateway.buscarTipoUsuarioPorNome(tipoUsuarioAlterado.tipoUsuario());

        if(tipoUsuarioExistente == null) {
            throw new TipoUsuarioNaoExisteException("Tipo de usuário não encontrado.");
        }

        TipoUsuario alterarTipoUsuario = TipoUsuario.create(tipoUsuarioAlterado.tipoUsuario());

        return tipoUsuarioGateway.alterar(alterarTipoUsuario);
    }
}
