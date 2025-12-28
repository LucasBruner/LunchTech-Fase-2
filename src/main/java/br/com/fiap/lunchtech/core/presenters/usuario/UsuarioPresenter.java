package br.com.fiap.lunchtech.core.presenters.usuario;

import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.entities.Usuario;

public class UsuarioPresenter {

    public static UsuarioDTO toDto(Usuario usuario) {
        return new UsuarioDTO(usuario.getNome(),
                usuario.getEnderecoEmail(),
                usuario.getLogin(),
                usuario.getTipoDeUsuario().getTipoUsuario());
    }

    public static UsuarioAlteradoDTO mostrarUsuarioAlterado(Usuario usuario) {
        return new UsuarioAlteradoDTO(usuario.getLogin());
    }
}
