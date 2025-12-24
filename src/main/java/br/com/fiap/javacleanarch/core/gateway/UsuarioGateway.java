package br.com.fiap.javacleanarch.core.gateway;

import br.com.fiap.javacleanarch.core.dto.NovoUsuarioDTO;
import br.com.fiap.javacleanarch.core.dto.UsuarioDTO;
import br.com.fiap.javacleanarch.core.entities.Usuario;
import br.com.fiap.javacleanarch.core.exceptions.EstudanteNaoEncontradoException;
import br.com.fiap.javacleanarch.core.interfaces.IDataSource;
import br.com.fiap.javacleanarch.core.interfaces.IUsuarioGateway;

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
            throw new EstudanteNaoEncontradoException("Login incorreto!");
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
}
