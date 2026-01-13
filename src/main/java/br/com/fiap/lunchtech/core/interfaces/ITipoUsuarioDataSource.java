package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;

import java.util.List;

public interface ITipoUsuarioDataSource {
    TipoUsuarioDTO incluirNovoTipoUsuario(TipoUsuarioDTO tipoUsuarioDTO);

    TipoUsuarioDTO alterarTipoUsuario(TipoUsuarioDTO tipoUsuarioDTO, String tipoUsuarioAntigo);

    void deletarTipoUsuario(String tipoUsuario);

    TipoUsuarioDTO buscarTipoUsuarioPorNome(String tipoUsuario);

    List<TipoUsuarioDTO> buscarTodosTipoUsuario();
}
