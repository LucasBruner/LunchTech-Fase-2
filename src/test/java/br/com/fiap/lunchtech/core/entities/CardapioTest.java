package br.com.fiap.lunchtech.core.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CardapioTest {

    private Restaurante criarRestauranteValido() {
        Endereco endereco = Endereco.create("Rua Teste", 123, "Bairro Teste", "Cidade Teste", "Estado Teste", "12345678");
        TipoUsuario tipoUsuario = TipoUsuario.create("DONO_RESTAURANTE");
        Usuario dono = Usuario.create("Dono", "dono@restaurante.com", "dono_login", "senha", tipoUsuario, endereco);
        return Restaurante.create("Restaurante Teste", "Cozinha Teste", new Date(), new Date(), endereco, dono);
    }

    @Test
    void deveCriarItemCardapioComSucesso() {
        Restaurante restaurante = criarRestauranteValido();
        Cardapio cardapio = Cardapio.create("Produto Teste", "Descrição Teste", 10.0, false, "foto.jpg", restaurante);

        assertNotNull(cardapio);
        assertEquals("Produto Teste", cardapio.getNomeProduto());
        assertEquals(10.0, cardapio.getPreco());
        assertEquals(restaurante, cardapio.getRestaurante());
    }

    @Test
    void deveLancarExcecaoParaNomeProdutoInvalido() {
        Restaurante restaurante = criarRestauranteValido();
        assertThrows(IllegalArgumentException.class, () -> Cardapio.create(null, "Descrição", 10.0, false, "foto.jpg", restaurante));
    }

    @Test
    void deveLancarExcecaoParaPrecoInvalido() {
        Restaurante restaurante = criarRestauranteValido();
        assertThrows(IllegalArgumentException.class, () -> Cardapio.create("Produto", "Descrição", -1.0, false, "foto.jpg", restaurante));
    }

    @Test
    void deveLancarExcecaoParaRestauranteInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Cardapio.create("Produto", "Descrição", 10.0, false, "foto.jpg", null));
    }
}
