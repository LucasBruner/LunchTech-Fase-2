package br.com.fiap.lunchtech.core.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {

    @Test
    void deveCriarEnderecoComSucesso() {
        Endereco endereco = Endereco.create("Rua Teste", 123, "Bairro Teste", "Cidade Teste", "Estado Teste", "12345678");
        assertNotNull(endereco);
        assertEquals("Rua Teste", endereco.getLogradouro());
        assertEquals(123, endereco.getNumero());
        assertEquals("Bairro Teste", endereco.getBairro());
        assertEquals("Cidade Teste", endereco.getCidade());
        assertEquals("Estado Teste", endereco.getEstado());
        assertEquals("12345678", endereco.getCep());
    }

    @Test
    void deveLancarExcecaoParaLogradouroInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Endereco.create(null, 123, "Bairro", "Cidade", "Estado", "12345678"));
        assertThrows(IllegalArgumentException.class, () -> Endereco.create(" ", 123, "Bairro", "Cidade", "Estado", "12345678"));
    }

    @Test
    void deveLancarExcecaoParaNumeroInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", 0, "Bairro", "Cidade", "Estado", "12345678"));
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", -1, "Bairro", "Cidade", "Estado", "12345678"));
    }

    @Test
    void deveLancarExcecaoParaBairroInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", 123, null, "Cidade", "Estado", "12345678"));
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", 123, " ", "Cidade", "Estado", "12345678"));
    }

    @Test
    void deveLancarExcecaoParaCidadeInvalida() {
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", 123, "Bairro", null, "Estado", "12345678"));
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", 123, "Bairro", " ", "Estado", "12345678"));
    }

    @Test
    void deveLancarExcecaoParaEstadoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", 123, "Bairro", "Cidade", null, "12345678"));
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", 123, "Bairro", "Cidade", " ", "12345678"));
    }

    @Test
    void deveLancarExcecaoParaCepInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", 123, "Bairro", "Cidade", "Estado", null));
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", 123, "Bairro", "Cidade", "Estado", " "));
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", 123, "Bairro", "Cidade", "Estado", "12345-678"));
        assertThrows(IllegalArgumentException.class, () -> Endereco.create("Rua", 123, "Bairro", "Cidade", "Estado", "1234567"));
    }
}
