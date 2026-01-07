package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.entities.TipoUsuario;

public interface ITipoUsuarioGateway {
    TipoUsuario incluir(TipoUsuario tipoUsuario);

    TipoUsuario alterar(TipoUsuario tipoUsuario);

    void deletar(String tipoUsuario);

    TipoUsuario buscarTipoUsuarioPorNome(String tipoUsuario);
}
