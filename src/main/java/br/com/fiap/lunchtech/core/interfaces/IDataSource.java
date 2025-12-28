package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.dto.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.UsuarioDTO;

import java.util.List;

public interface IDataSource {
    UsuarioDTO obterUsuarioPorLogin(String login);

    UsuarioDTO incluirNovoUsuario(NovoUsuarioDTO novoUsuarioDTO);

    List<UsuarioDTO> buscarUsuariosPorNome(String nomeUsuario);
}
