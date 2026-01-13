package br.com.fiap.lunchtech.core.usecases.tipousuario;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoDeUsuarioNaoPodeSerExcluidoException;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;

public class DeletarTipoUsuarioUseCase {
    private ITipoUsuarioGateway tipoUsuarioGateway;

    private DeletarTipoUsuarioUseCase(ITipoUsuarioGateway tipoUsuarioGateway) {
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public static DeletarTipoUsuarioUseCase create(ITipoUsuarioGateway tipoUsuarioGateway) {
        return new DeletarTipoUsuarioUseCase(tipoUsuarioGateway);
    }

    public void run(TipoUsuarioDTO tipoUsuarioDTO) {
        if("CLIENTE".equals(tipoUsuarioDTO.tipoUsuario()) || "DONO_RESTAURANTE".equals(tipoUsuarioDTO.tipoUsuario())) {
            throw new TipoDeUsuarioNaoPodeSerExcluidoException("Tipo de usuário não pode ser excluído.");
        }

        TipoUsuario tipoUsuario = tipoUsuarioGateway.buscarTipoUsuarioPorNome(tipoUsuarioDTO.tipoUsuario());

        if(tipoUsuario == null) {
            throw new TipoUsuarioNaoExisteException("Tipo de usuário não encontrado.");
        }

        tipoUsuarioGateway.deletar(tipoUsuario.getTipoUsuario());
    }
}
