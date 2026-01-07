package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;

public interface ITipoUsuarioDataSource {
    TipoUsuarioDTO incluirNovoTipoUsuario(TipoUsuarioDTO tipoUsuarioDTO);

    TipoUsuarioDTO alterarTipoUsuario(TipoUsuarioDTO tipoUsuarioDTO);

    void deletarTipoUsuario(String tipoUsuario);

    TipoUsuarioDTO buscarTipoUsuarioPorNome(String tipoUsuario);
}
