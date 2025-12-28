package br.com.fiap.lunchtech.core.interfaces;

import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;

import java.util.List;

public interface IDataSource {
    UsuarioDTO obterUsuarioPorLogin(String login);

    UsuarioDTO incluirNovoUsuario(NovoUsuarioDTO novoUsuarioDTO);

    List<UsuarioDTO> buscarUsuariosPorNome(String nomeUsuario);

    UsuarioDTO buscarUsuarioPorEmail(String emailUsuario);

    UsuarioDTO alterarUsuario(UsuarioAlteracaoDTO usuarioAlteracaoDTO);

    void deletarUsuario(String login);

    void alterarSenhaUsuario(UsuarioSenhaDTO usuarioSenhaDTO);

    UsuarioSenhaDTO buscarDadosUsuarioPorLogin(String login);
}
