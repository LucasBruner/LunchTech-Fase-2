package br.com.fiap.lunchtech.core.usecases.tipousuario;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoDeUsuarioExisteException;
import br.com.fiap.lunchtech.core.exceptions.TipoDeUsuarioNaoPodeSerExcluidoException;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;

public class AlterarTipoUsuarioUseCase {
    private final ITipoUsuarioGateway tipoUsuarioGateway;

    private AlterarTipoUsuarioUseCase(ITipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public static AlterarTipoUsuarioUseCase create(ITipoUsuarioGateway tipoUsuarioGateway) {
        return new AlterarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    public TipoUsuario run(TipoUsuarioAlteracaoDTO tipoUsuarioAlteradoDTO) {
        if("CLIENTE".equals(tipoUsuarioAlteradoDTO.tipoUsuario()) || "DONO_RESTAURANTE".equals(tipoUsuarioAlteradoDTO.tipoUsuario())) {
            throw new TipoDeUsuarioNaoPodeSerExcluidoException("Tipo de usuário não pode ser alterado.");
        }
        tipoUsuarioGateway.buscarTipoUsuarioPorNome(tipoUsuarioAlteradoDTO.tipoUsuario());

        TipoUsuario novoTipoUsuario;
        try {
            novoTipoUsuario = tipoUsuarioGateway.buscarTipoUsuarioPorNome(tipoUsuarioAlteradoDTO.novoTipoUsuario());
        } catch (TipoUsuarioNaoExisteException _) {
            novoTipoUsuario = null;
        }

        if(novoTipoUsuario != null) {
            throw new TipoDeUsuarioExisteException("Tipo de usuário já está cadastrado.");
        }

        TipoUsuario alterarTipoUsuario = TipoUsuario.create(tipoUsuarioAlteradoDTO.novoTipoUsuario().toUpperCase());

        return tipoUsuarioGateway.alterar(alterarTipoUsuario, tipoUsuarioAlteradoDTO.tipoUsuario());
    }
}
