package br.com.fiap.lunchtech.core.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {

    @Test
    void deveCriarRestauranteComSucesso() {
        Endereco endereco = Endereco.create("Rua Teste", 123, "Bairro Teste", "Cidade Teste", "Estado Teste", "12345678");
        TipoUsuario tipoUsuario = TipoUsuario.create("DONO_RESTAURANTE");
        Usuario dono = Usuario.create("Dono", "dono@restaurante.com", "dono_login", "senha", tipoUsuario, endereco);
        Date inicio = new Date();
        Date fim = new Date();

        Restaurante restaurante = Restaurante.create("Restaurante Teste", "Cozinha Teste", inicio, fim, endereco, dono);

        assertNotNull(restaurante);
        assertEquals("Restaurante Teste", restaurante.getNome());
        assertEquals("Cozinha Teste", restaurante.getTipoCozinha());
        assertEquals(dono, restaurante.getDonoRestaurante());
    }

    @Test
    void deveLancarExcecaoParaNomeInvalido() {
        Endereco endereco = Endereco.create("Rua", 1, "Bairro", "Cidade", "Estado", "12345678");
        TipoUsuario tipoUsuario = TipoUsuario.create("DONO_RESTAURANTE");
        Usuario dono = Usuario.create("Dono", "dono@restaurante.com", "dono_login", "senha", tipoUsuario, endereco);
        assertThrows(IllegalArgumentException.class, () -> Restaurante.create(null, "Cozinha", new Date(), new Date(), endereco, dono));
    }

    @Test
    void deveLancarExcecaoParaTipoCozinhaInvalido() {
        Endereco endereco = Endereco.create("Rua", 1, "Bairro", "Cidade", "Estado", "12345678");
        TipoUsuario tipoUsuario = TipoUsuario.create("DONO_RESTAURANTE");
        Usuario dono = Usuario.create("Dono", "dono@restaurante.com", "dono_login", "senha", tipoUsuario, endereco);
        assertThrows(IllegalArgumentException.class, () -> Restaurante.create("Restaurante", null, new Date(), new Date(), endereco, dono));
    }

    @Test
    void deveLancarExcecaoParaDonoInvalido() {
        Endereco endereco = Endereco.create("Rua", 1, "Bairro", "Cidade", "Estado", "12345678");
        assertThrows(IllegalArgumentException.class, () -> Restaurante.create("Restaurante", "Cozinha", new Date(), new Date(), endereco, null));
    }

    @Test
    void deveLancarExcecaoSeDonoNaoForDonoDeRestaurante() {
        Endereco endereco = Endereco.create("Rua", 1, "Bairro", "Cidade", "Estado", "12345678");
        TipoUsuario tipoUsuario = TipoUsuario.create("CLIENTE");
        Usuario cliente = Usuario.create("Cliente", "cliente@email.com", "cliente_login", "senha", tipoUsuario, endereco);
        assertThrows(IllegalArgumentException.class, () -> Restaurante.create("Restaurante", "Cozinha", new Date(), new Date(), endereco, cliente));
    }
}
