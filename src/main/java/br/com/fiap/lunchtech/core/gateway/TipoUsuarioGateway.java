package br.com.fiap.lunchtech.core.gateway;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.exceptions.TipoUsuarioNaoExisteException;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;

public class TipoUsuarioGateway implements ITipoUsuarioGateway {
    ITipoUsuarioDataSource tipoUsuarioDataSource;

    private TipoUsuarioGateway(ITipoUsuarioDataSource tipoUsuarioDataSource) {
        this.tipoUsuarioDataSource = tipoUsuarioDataSource;
    }

    public static TipoUsuarioGateway create(ITipoUsuarioDataSource tipoUsuarioDataSource) {
        return new TipoUsuarioGateway(tipoUsuarioDataSource);
    }

    @Override
    public TipoUsuario incluir(TipoUsuario tipoUsuario) {
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO(tipoUsuario.getTipoUsuario());
        TipoUsuarioDTO tipoUsuarioCriado = tipoUsuarioDataSource.incluirNovoTipoUsuario(tipoUsuarioDTO);
        return TipoUsuario.create(tipoUsuarioCriado.tipoUsuario());
    }

    @Override
    public TipoUsuario alterar(TipoUsuario tipoUsuario) {
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO(tipoUsuario.getTipoUsuario());
        TipoUsuarioDTO tipoUsuarioAlterado = tipoUsuarioDataSource.alterarTipoUsuario(tipoUsuarioDTO);
        return TipoUsuario.create(tipoUsuarioAlterado.tipoUsuario());
    }

    @Override
    public void deletar(String tipoUsuario) {
        tipoUsuarioDataSource.deletarTipoUsuario(tipoUsuario);
    }

    @Override
    public TipoUsuario buscarTipoUsuarioPorNome(String tipoUsuario) {
        TipoUsuarioDTO tipoUsuarioDTO = tipoUsuarioDataSource.buscarTipoUsuarioPorNome(tipoUsuario);

        if(tipoUsuarioDTO == null) {
            throw new TipoUsuarioNaoExisteException("Tipo de usuário não encontrado.");
        }

        return TipoUsuario.create(tipoUsuarioDTO.tipoUsuario());
    }
}
