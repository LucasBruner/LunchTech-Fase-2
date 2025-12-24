package br.com.fiap.javacleanarch.core.presenters;

import br.com.fiap.javacleanarch.core.dto.UsuarioDTO;
import br.com.fiap.javacleanarch.core.entities.Usuario;

public class UsuarioPresenter {

    public static UsuarioDTO toDto(Usuario usuario) {
        return new UsuarioDTO(usuario.getNome(),
                usuario.getEnderecoEmail(),
                usuario.getLogin(),
                usuario.getTipoDeUsuario());
    }

}
