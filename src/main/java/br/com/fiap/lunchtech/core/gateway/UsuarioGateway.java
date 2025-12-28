package br.com.fiap.lunchtech.core.gateway;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
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

        final EnderecoDTO enderecoDTO = new EnderecoDTO(novoUsuario.getEndereco().getLogradouro(),
                novoUsuario.getEndereco().getNumero(),
                novoUsuario.getEndereco().getBairro(),
                novoUsuario.getEndereco().getCidade(),
                novoUsuario.getEndereco().getEstado(),
                novoUsuario.getEndereco().getCep());

        final NovoUsuarioDTO novoUsuarioDTO = new NovoUsuarioDTO(novoUsuario.getNome(),
                novoUsuario.getEmail(),
                novoUsuario.getLogin(),
                novoUsuario.getSenha(),
                novoUsuario.getTipoDeUsuario().getTipoUsuario(),
                enderecoDTO);

        UsuarioDTO usuarioCriado = this.dataSource.incluirNovoUsuario(novoUsuarioDTO);
        var tipoUsuario = TipoUsuario.create(usuarioCriado.tipoDeUsuario());

        var enderecoUsuario = buscarValoresEndereco(usuarioCriado.endereco());

        return Usuario.create(usuarioCriado.nomeUsuario(),
                usuarioCriado.enderecoEmail(),
                usuarioCriado.login(),
                tipoUsuario,
                enderecoUsuario
        );
    }

    @Override
    public List<Usuario> buscarPorNome(String nomeUsuario) {
        List<UsuarioDTO> usuariosDTO = this.dataSource.buscarUsuariosPorNome(nomeUsuario);

        return usuariosDTO.stream()
                .map(usuarioDTO -> Usuario.create(usuarioDTO.nomeUsuario(),
                        usuarioDTO.enderecoEmail(),
                        usuarioDTO.login(),
                        TipoUsuario.create(usuarioDTO.tipoDeUsuario()),
                        buscarValoresEndereco(usuarioDTO.endereco())
                        ))
                .toList();
    }

    @Override
    public boolean buscarPorEmail(String emailUsuario) {
        UsuarioDTO usuarioDTO = this.dataSource.buscarUsuarioPorEmail(emailUsuario);
        return usuarioDTO != null;
    }

    @Override
    public Usuario alterar(Usuario usuarioAlteracao) {

        final EnderecoDTO enderecoUsuario = new EnderecoDTO(usuarioAlteracao.getEndereco().getLogradouro(),
                usuarioAlteracao.getEndereco().getNumero(),
                usuarioAlteracao.getEndereco().getBairro(),
                usuarioAlteracao.getEndereco().getCidade(),
                usuarioAlteracao.getEndereco().getEstado(),
                usuarioAlteracao.getEndereco().getCep());

        final UsuarioAlteracaoDTO usuarioAlteracaoDTO = new UsuarioAlteracaoDTO(usuarioAlteracao.getNome(),
                usuarioAlteracao.getEmail(),
                usuarioAlteracao.getLogin(),
                usuarioAlteracao.getTipoDeUsuario().getTipoUsuario(),
                enderecoUsuario);

        UsuarioDTO usuarioAlterado = this.dataSource.alterarUsuario(usuarioAlteracaoDTO);

        final Endereco enderecoUsuarioAlterado = buscarValoresEndereco(usuarioAlterado.endereco());

        return Usuario.create(usuarioAlterado.nomeUsuario(),
                usuarioAlterado.enderecoEmail(),
                usuarioAlterado.login(),
                TipoUsuario.create(usuarioAlterado.tipoDeUsuario()),
                enderecoUsuarioAlterado
                );
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
        UsuarioSenhaDTO usuarioValido = this.dataSource.buscarDadosUsuarioPorLogin(login);

        if(usuarioValido == null) {
            throw new UsuarioNaoEncontradoException("Login incorreto!");
        }

        return Usuario.create(usuarioValido.login(), usuarioValido.senha());
    }

    private Endereco buscarValoresEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.create(enderecoDTO.logradouro(),
                enderecoDTO.numero(),
                enderecoDTO.bairro(),
                enderecoDTO.cidade(),
                enderecoDTO.estado(),
                enderecoDTO.cep());
    }
}
