package br.com.fiap.lunchtech.core.presenters.usuario;

import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.entities.Usuario;

public class UsuarioPresenter {

    public static UsuarioDTO toDto(Usuario usuario) {
        return new UsuarioDTO(usuario.getNome(),
                usuario.getEnderecoEmail(),
                usuario.getLogin(),
                usuario.getTipoDeUsuario());
    }
}
