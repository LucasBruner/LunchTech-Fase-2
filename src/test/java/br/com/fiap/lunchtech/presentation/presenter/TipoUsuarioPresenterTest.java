package br.com.fiap.lunchtech.presentation.presenter;

import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.presenters.TipoUsuarioPresenter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoUsuarioPresenterTest {

    @Test
    void deveConverterParaDto() {
        TipoUsuario tipoUsuario = TipoUsuario.create("TESTE");
        TipoUsuarioDTO dto = TipoUsuarioPresenter.toDto(tipoUsuario);
        assertNotNull(dto);
        assertEquals("TESTE", dto.tipoUsuario());
    }
}
