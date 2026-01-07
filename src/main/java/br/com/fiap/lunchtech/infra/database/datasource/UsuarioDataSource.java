package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.infra.database.repositories.IUsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioDataSource implements IUsuarioDataSource {
    private final IUsuarioRepository usuarioRepository;

    public UsuarioDataSource(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioDTO obterUsuarioPorLogin(String login) {
        return null;
    }

    @Override
    public UsuarioDTO incluirNovoUsuario(NovoUsuarioDTO novoUsuarioDTO) {
        return null;
    }

    @Override
    public List<UsuarioDTO> buscarUsuariosPorNome(String nomeUsuario) {
        return List.of();
    }

    @Override
    public UsuarioDTO buscarUsuarioPorEmail(String emailUsuario) {
        return null;
    }

    @Override
    public UsuarioDTO alterarUsuario(UsuarioAlteracaoDTO usuarioAlteracaoDTO) {
        return null;
    }

    @Override
    public void deletarUsuario(String login) {

    }

    @Override
    public void alterarSenhaUsuario(UsuarioSenhaDTO usuarioSenhaDTO) {

    }

    @Override
    public UsuarioSenhaDTO buscarDadosUsuarioPorLogin(String login) {
        return null;
    }
}
