package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoUsuarioTest {

    @Test
    void deveCriarTipoUsuarioComSucesso() {
        TipoUsuario tipoUsuario = TipoUsuario.create("ADMIN");
        assertNotNull(tipoUsuario);
        assertEquals("ADMIN", tipoUsuario.getTipoUsuario());
    }

    @Test
    void deveLancarExcecaoParaTipoUsuarioInvalido() {
        assertThrows(IllegalArgumentException.class, () -> TipoUsuario.create(null));
        assertThrows(IllegalArgumentException.class, () -> TipoUsuario.create(" "));
    }
}
