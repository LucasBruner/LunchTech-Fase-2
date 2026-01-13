package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.entities.TipoUsuario;

import java.util.List;

public interface ITipoUsuarioGateway {
    TipoUsuario incluir(TipoUsuario tipoUsuario);

    TipoUsuario alterar(TipoUsuario tipoUsuario, String tipoUsuarioAntigo);

    void deletar(String tipoUsuario);

    TipoUsuario buscarTipoUsuarioPorNome(String tipoUsuario);

    List<TipoUsuario> buscarTodosTipoUsuario();
}
