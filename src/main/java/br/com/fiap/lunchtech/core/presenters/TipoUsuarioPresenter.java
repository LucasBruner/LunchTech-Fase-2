package br.com.fiap.lunchtech.core.presenters;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;

public class TipoUsuarioPresenter {

    public static TipoUsuarioDTO toDto(TipoUsuario tipoUsuario) {
        return new TipoUsuarioDTO(tipoUsuario.getTipoUsuario());
    }
}
