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
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioGateway;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

import java.util.List;

public class UsuarioGateway implements IUsuarioGateway {
    private final IUsuarioDataSource dataSource;
    private ITipoUsuarioGateway tipoUsuarioGateway;

    public UsuarioGateway(IUsuarioDataSource dataStorageSource,
                          ITipoUsuarioGateway tipoUsuarioGateway) {
        this.dataSource = dataStorageSource;
        this.tipoUsuarioGateway = tipoUsuarioGateway;
    }

    public static UsuarioGateway create(IUsuarioDataSource dataStorageSource,
                                        ITipoUsuarioGateway tipoUsuarioGateway) {
        return new UsuarioGateway(dataStorageSource, tipoUsuarioGateway);
    }

    public UsuarioGateway(IUsuarioDataSource dataStorageSource) {
        this.dataSource = dataStorageSource;
    }

    public static UsuarioGateway create(IUsuarioDataSource dataStorageSource) {
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
    public Usuario buscarPorLoginExistente(String login) {
        UsuarioDTO usuarioDTO = this.dataSource.obterUsuarioPorLogin(login);

        if (usuarioDTO == null) {
            return null;
        }

        var tipoUsuario = TipoUsuario.create(usuarioDTO.tipoDeUsuario());

        return Usuario.create(usuarioDTO.nomeUsuario(),
                usuarioDTO.enderecoEmail(),
                usuarioDTO.login(),
                tipoUsuario);
    }

    @Override
    public Usuario incluir(Usuario novoUsuario) {
        final NovoUsuarioDTO novoUsuarioDTO = getNovoUsuarioDTO(novoUsuario);

        UsuarioDTO usuarioCriado = this.dataSource.incluirNovoUsuario(novoUsuarioDTO);
        var tipoUsuario = TipoUsuario.create(usuarioCriado.tipoDeUsuario());

        var enderecoUsuario = mapearEndereco(usuarioCriado.endereco());

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
                        mapearEndereco(usuarioDTO.endereco())
                        ))
                .toList();
    }

    @Override
    public List<Usuario> buscarTodos() {
        List<UsuarioDTO> usuariosDTO = this.dataSource.buscarUsuarios();

        return usuariosDTO.stream()
                .map(usuarioDTO -> Usuario.create(usuarioDTO.nomeUsuario(),
                        usuarioDTO.enderecoEmail(),
                        usuarioDTO.login(),
                        TipoUsuario.create(usuarioDTO.tipoDeUsuario()),
                        mapearEndereco(usuarioDTO.endereco())
                ))
                .toList();
    }

    @Override
    public boolean buscarPorEmail(String emailUsuario) {
        UsuarioDTO usuarioDTO = this.dataSource.buscarUsuarioPorEmail(emailUsuario);
        return usuarioDTO != null;
    }

    @Override
    public boolean buscarSeEmailExistente(String emailAlteracao, String login) {
        UsuarioDTO usuarioDTO = this.dataSource.buscarUsuarioPorEmail(emailAlteracao);
        return usuarioDTO != null && !usuarioDTO.login().equals(login);
    }

    @Override
    public Usuario alterar(Usuario usuarioAlteracao) {

        final UsuarioAlteracaoDTO usuarioAlteracaoDTO = mapearUsuarioAlteracaoDTO(usuarioAlteracao);

        UsuarioDTO usuarioAlterado = this.dataSource.alterarUsuario(usuarioAlteracaoDTO);

        final Endereco enderecoUsuarioAlterado = mapearEndereco(usuarioAlterado.endereco());

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
                usuarioAlteracaoSenha.getSenha(),
                usuarioAlteracaoSenha.getDataAtualizacao());

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

    @Override
    public boolean buscarSeTipoUsuarioExistente(String tipo) {
        TipoUsuario tipoUsuario = tipoUsuarioGateway.buscarTipoUsuarioPorNome(tipo);
        return tipoUsuario != null;
    }

    private Endereco mapearEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.create(enderecoDTO.logradouro(),
                enderecoDTO.numero(),
                enderecoDTO.bairro(),
                enderecoDTO.cidade(),
                enderecoDTO.estado(),
                enderecoDTO.cep());
    }

    private NovoUsuarioDTO getNovoUsuarioDTO(Usuario novoUsuario) {
        final EnderecoDTO enderecoDTO = mapearEnderecoDTO(novoUsuario.getEndereco());

        return new NovoUsuarioDTO(novoUsuario.getNome(),
                novoUsuario.getEmail(),
                novoUsuario.getLogin(),
                novoUsuario.getSenha(),
                novoUsuario.getTipoDeUsuario().getTipoUsuario(),
                enderecoDTO,
                novoUsuario.getDataAtualizacao());
    }


    private UsuarioAlteracaoDTO mapearUsuarioAlteracaoDTO(Usuario usuarioAlteracao) {
        final EnderecoDTO enderecoUsuario = mapearEnderecoDTO(usuarioAlteracao.getEndereco());

        return new UsuarioAlteracaoDTO(usuarioAlteracao.getNome(),
                usuarioAlteracao.getEmail(),
                usuarioAlteracao.getLogin(),
                usuarioAlteracao.getTipoDeUsuario().getTipoUsuario(),
                enderecoUsuario,
                usuarioAlteracao.getDataAtualizacao());
    }

    private EnderecoDTO mapearEnderecoDTO(Endereco endereco) {
        return new EnderecoDTO(endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep());
    }
}
