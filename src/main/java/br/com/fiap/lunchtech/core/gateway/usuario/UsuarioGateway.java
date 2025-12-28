package br.com.fiap.lunchtech.core.gateway.usuario;

import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

import java.util.List;

public class UsuarioGateway implements IUsuarioGateway {
    private IDataSource dataSource;
    public UsuarioGateway(IDataSource dataStorageSource) {
        this.dataSource = dataStorageSource;
    }

    public static UsuarioGateway create(IDataSource dataStorageSource) {
        return new UsuarioGateway(dataStorageSource);
    }

    @Override
    public Usuario buscarPorLogin(String login) {
        UsuarioDTO usuarioDTO = this.dataSource.obterUsuarioPorLogin(login);

        if(usuarioDTO == null) {
            throw new UsuarioNaoEncontradoException("Login incorreto!");
        }

        return Usuario.create(usuarioDTO.nomeUsuario(),
                usuarioDTO.enderecoEmail(),
                usuarioDTO.login(),
                usuarioDTO.tipoDeUsuario());
    }

    @Override
    public Usuario incluir(Usuario novoUsuario) {

        final NovoUsuarioDTO novoUsuarioDTO = new NovoUsuarioDTO(novoUsuario.getNome(),
                novoUsuario.getEnderecoEmail(),
                novoUsuario.getLogin(),
                novoUsuario.getSenha(),
                novoUsuario.getTipoDeUsuario());

        UsuarioDTO usuarioCriado = this.dataSource.incluirNovoUsuario(novoUsuarioDTO);

        return Usuario.create(usuarioCriado.nomeUsuario(), usuarioCriado.enderecoEmail(), usuarioCriado.login(), usuarioCriado.tipoDeUsuario());
    }

    @Override
    public List<Usuario> buscarPorNome(String nomeUsuario) {
        List<UsuarioDTO> usuariosDTO = this.dataSource.buscarUsuariosPorNome(nomeUsuario);

        List<Usuario> usuarios = usuariosDTO.stream()
                .map(usuarioDTO -> Usuario.create(usuarioDTO.nomeUsuario(),
                        usuarioDTO.enderecoEmail(),
                        usuarioDTO.login(),
                        usuarioDTO.tipoDeUsuario()))
                .toList();

        return usuarios;
    }
}
