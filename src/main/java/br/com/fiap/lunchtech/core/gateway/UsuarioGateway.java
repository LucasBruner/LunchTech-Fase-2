package br.com.fiap.lunchtech.core.gateway;

import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAutenticadoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
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

        var tipoUsuario = TipoUsuario.create(usuarioDTO.tipoDeUsuario());

        return Usuario.create(usuarioDTO.nomeUsuario(),
                usuarioDTO.enderecoEmail(),
                usuarioDTO.login(),
                tipoUsuario);
    }

    @Override
    public Usuario incluir(Usuario novoUsuario) {

        final NovoUsuarioDTO novoUsuarioDTO = new NovoUsuarioDTO(novoUsuario.getNome(),
                novoUsuario.getEnderecoEmail(),
                novoUsuario.getLogin(),
                novoUsuario.getSenha(),
                novoUsuario.getTipoDeUsuario().getTipoUsuario());

        UsuarioDTO usuarioCriado = this.dataSource.incluirNovoUsuario(novoUsuarioDTO);
        var tipoUsuario = TipoUsuario.create(usuarioCriado.tipoDeUsuario());

        return Usuario.create(usuarioCriado.nomeUsuario(), usuarioCriado.enderecoEmail(), usuarioCriado.login(), tipoUsuario);
    }

    @Override
    public List<Usuario> buscarPorNome(String nomeUsuario) {
        List<UsuarioDTO> usuariosDTO = this.dataSource.buscarUsuariosPorNome(nomeUsuario);

        return usuariosDTO.stream()
                .map(usuarioDTO -> Usuario.create(usuarioDTO.nomeUsuario(),
                        usuarioDTO.enderecoEmail(),
                        usuarioDTO.login(),
                        TipoUsuario.create(usuarioDTO.tipoDeUsuario())))
                .toList();
    }

    @Override
    public boolean buscarPorEmail(String emailUsuario) {
        UsuarioDTO usuarioDTO = this.dataSource.buscarUsuarioPorEmail(emailUsuario);
        return usuarioDTO != null;
    }

    @Override
    public Usuario alterar(Usuario usuarioAlteracao) {

        final UsuarioAlteracaoDTO usuarioAlteracaoDTO = new UsuarioAlteracaoDTO(usuarioAlteracao.getNome(),
                usuarioAlteracao.getEnderecoEmail(),
                usuarioAlteracao.getLogin(),
                usuarioAlteracao.getTipoDeUsuario().getTipoUsuario());

        UsuarioDTO usuarioCriado = this.dataSource.alterarUsuario(usuarioAlteracaoDTO);

        return Usuario.create(usuarioCriado.nomeUsuario(), usuarioCriado.enderecoEmail(), usuarioCriado.login(), TipoUsuario.create(usuarioCriado.tipoDeUsuario()));
    }

    @Override
    public void deletar(String login) {
        this.dataSource.deletarUsuario(login);
    }

    @Override
    public Usuario alterarSenha(Usuario usuarioAlteracaoSenha) {
        UsuarioDTO usuarioDTO = this.dataSource.obterUsuarioPorLogin(usuarioAlteracaoSenha.getLogin());

        if(usuarioDTO == null) {
            throw new UsuarioNaoEncontradoException("O usuário informado para troca de senha não foi encontrado!");
        }

        final UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO(usuarioAlteracaoSenha.getLogin(),
                usuarioAlteracaoSenha.getSenha());

        this.dataSource.alterarSenhaUsuario(usuarioSenhaDTO);

        return Usuario.create(usuarioSenhaDTO.login(), usuarioSenhaDTO.senha());
    }

    @Override
    public Usuario buscarDadosLogin(String login) {
        UsuarioAutenticadoDTO usuarioValido = this.dataSource.buscarDadosUsuarioPorLogin(login);

        if(usuarioValido == null) {
            throw new UsuarioNaoEncontradoException("Login incorreto!");
        }

        return Usuario.create(usuarioValido.nomeUsuario(),
                usuarioValido.enderecoEmail(),
                usuarioValido.login(),
                usuarioValido.senha(),
                TipoUsuario.create(usuarioValido.tipoDeUsuario()));
    }
}
