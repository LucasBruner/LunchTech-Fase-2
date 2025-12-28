package br.com.fiap.lunchtech.core.presenters;

import br.com.fiap.lunchtech.core.dto.UsuarioDTO;
import br.com.fiap.lunchtech.core.entities.Usuario;

public class UsuarioPresenter {

    public static UsuarioDTO toDto(Usuario usuario) {
        return new UsuarioDTO(usuario.getNome(),
                usuario.getEnderecoEmail(),
                usuario.getLogin(),
                usuario.getTipoDeUsuario());
    }

}
