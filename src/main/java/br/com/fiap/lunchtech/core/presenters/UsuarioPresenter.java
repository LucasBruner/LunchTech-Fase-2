package br.com.fiap.lunchtech.core.presenters;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.entities.Usuario;

public class UsuarioPresenter {

    public static UsuarioDTO toDto(Usuario usuario) {
        EnderecoDTO enderecoDTO = new EnderecoDTO(usuario.getEndereco().getLogradouro(),
                usuario.getEndereco().getNumero(),
                usuario.getEndereco().getBairro(),
                usuario.getEndereco().getCidade(),
                usuario.getEndereco().getEstado(),
                usuario.getEndereco().getCep());

        return new UsuarioDTO(usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getTipoDeUsuario().getTipoUsuario(),
                enderecoDTO);
    }

    public static UsuarioAlteradoDTO mostrarUsuarioAlterado(Usuario usuario) {
        return new UsuarioAlteradoDTO(usuario.getLogin());
    }
}
