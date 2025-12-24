package br.com.fiap.javacleanarch.core.interfaces;

import br.com.fiap.javacleanarch.core.dto.NovoUsuarioDTO;
import br.com.fiap.javacleanarch.core.dto.UsuarioDTO;

public interface IDataSource {
    UsuarioDTO obterUsuarioPorLogin(String login);

    UsuarioDTO incluirNovoUsuario(NovoUsuarioDTO novoUsuarioDTO);
}
