package br.com.fiap.lunchtech.core.usecases.tipousuario;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioJaExisteException;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;

public class CadastrarTipoUsuarioUseCase {
    private ITipoUsuarioGateway tipoUsuarioGateway;

    private CadastrarTipoUsuarioUseCase(ITipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public static CadastrarTipoUsuarioUseCase create(ITipoUsuarioGateway tipoUsuarioGateway) {
        return new CadastrarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    public TipoUsuario run(TipoUsuarioDTO tipoUsuarioCriado) {
        TipoUsuario tipoUsuarioExistente;
        try {
            tipoUsuarioExistente = tipoUsuarioGateway.buscarTipoUsuarioPorNome(tipoUsuarioCriado.tipoUsuario());
        } catch (TipoUsuarioNaoExisteException e) {
            tipoUsuarioExistente = null;
        }

        if(tipoUsuarioExistente != null) {
            throw new TipoUsuarioJaExisteException("O tipo de usuário solicitado para adicionar já existe!.");
        }
        TipoUsuario alterarTipoUsuario = TipoUsuario.create(tipoUsuarioCriado.tipoUsuario().toUpperCase());

        return tipoUsuarioGateway.incluir(alterarTipoUsuario);
    }
}
