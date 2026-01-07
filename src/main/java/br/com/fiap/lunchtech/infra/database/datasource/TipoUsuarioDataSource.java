package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.infra.database.repositories.ITipoUsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class TipoUsuarioDataSource implements ITipoUsuarioDataSource {
    private ITipoUsuarioRepository tipoUsuarioRepository;

    public TipoUsuarioDataSource(ITipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Override
    public TipoUsuarioDTO incluirNovoTipoUsuario(TipoUsuarioDTO tipoUsuarioDTO) {
        return null;
    }

    @Override
    public TipoUsuarioDTO alterarTipoUsuario(TipoUsuarioDTO tipoUsuarioDTO) {
        return null;
    }

    @Override
    public void deletarTipoUsuario(String tipoUsuario) {

    }

    @Override
    public TipoUsuarioDTO buscarTipoUsuarioPorNome(String tipoUsuario) {
        return null;
    }
}
