package br.com.fiap.lunchtech.core.usecases.usuario;

import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.UsuarioComEmailJaCadastradoException;
import br.com.fiap.lunchtech.core.exceptions.UsuarioNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

public class AlterarUsuarioUseCase {
    IUsuarioGateway usuarioGateway;

    private AlterarUsuarioUseCase(IUsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    public static AlterarUsuarioUseCase create (IUsuarioGateway usuarioGateway) {
        return new AlterarUsuarioUseCase(usuarioGateway);
    }

    public Usuario run (UsuarioAlteracaoDTO usuarioAlteracaoDTO) {
        Usuario usuarioExistente = usuarioGateway.buscarPorLogin(usuarioAlteracaoDTO.login());

        if (usuarioExistente == null) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado para a ação solicitada! O login não pode ser alterado!");
        }

        boolean emailJaCadastrado = usuarioGateway.buscarSeEmailExistente(usuarioAlteracaoDTO.enderecoEmail(), usuarioExistente.getLogin());

        if (emailJaCadastrado) {
            throw new UsuarioComEmailJaCadastradoException("Esse e-mail já está sendo utilizado por outro usuário.");
        }

        Endereco endereco = Endereco.create(usuarioAlteracaoDTO.endereco().logradouro(),
                usuarioAlteracaoDTO.endereco().numero(),
                usuarioAlteracaoDTO.endereco().bairro(),
                usuarioAlteracaoDTO.endereco().cidade(),
                usuarioAlteracaoDTO.endereco().estado(),
                usuarioAlteracaoDTO.endereco().cep());

        Usuario usuarioAlteracao = Usuario.create(usuarioAlteracaoDTO.nomeUsuario(),
                usuarioAlteracaoDTO.enderecoEmail(),
                usuarioAlteracaoDTO.login(),
                TipoUsuario.create(usuarioAlteracaoDTO.tipoDeUsuario()),
                endereco);

        return usuarioGateway.alterar(usuarioAlteracao);
    }
}
