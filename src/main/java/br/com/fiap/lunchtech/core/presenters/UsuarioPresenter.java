package br.com.fiap.lunchtech.core.presenters;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Usuario;

public class UsuarioPresenter {

    public static UsuarioDTO toDto(Usuario usuario) {
        EnderecoDTO enderecoDTO = buscarEnderecoUsuario(usuario.getEndereco());

        return new UsuarioDTO(usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipoDeUsuario().getTipoUsuario(),
                enderecoDTO);
    }

    public static UsuarioAlteradoDTO mostrarUsuarioAlterado(Usuario usuario) {
        return new UsuarioAlteradoDTO(usuario.getLogin());
    }

    private static EnderecoDTO buscarEnderecoUsuario(Endereco enderecoUsuario) {
        return new EnderecoDTO(enderecoUsuario.getLogradouro(),
                enderecoUsuario.getNumero(),
                enderecoUsuario.getBairro(),
                enderecoUsuario.getCidade(),
                enderecoUsuario.getEstado(),
                enderecoUsuario.getCep());
    }
}
