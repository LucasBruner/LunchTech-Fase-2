package br.com.fiap.lunchtech.domain;

import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void deveCriarUsuarioCompletoComSucesso() {
        Endereco endereco = Endereco.create("Rua Teste", 123, "Bairro Teste", "Cidade Teste", "Estado Teste", "12345678");
        TipoUsuario tipoUsuario = TipoUsuario.create("CLIENTE");
        Usuario usuario = Usuario.create("Nome Teste", "email@teste.com", "login", "senha", tipoUsuario, endereco);

        assertNotNull(usuario);
        assertEquals("Nome Teste", usuario.getNome());
        assertEquals("email@teste.com", usuario.getEmail());
        assertEquals("login", usuario.getLogin());
        assertEquals("senha", usuario.getSenha());
        assertEquals(tipoUsuario, usuario.getTipoDeUsuario());
        assertEquals(endereco, usuario.getEndereco());
    }

    @Test
    void deveCriarUsuarioParaAlteracaoDeSenhaComSucesso() {
        Usuario usuario = Usuario.create("login", "nova_senha");

        assertNotNull(usuario);
        assertEquals("login", usuario.getLogin());
        assertEquals("nova_senha", usuario.getSenha());
    }

    @Test
    void deveLancarExcecaoParaNomeInvalido() {
        TipoUsuario tipoUsuario = TipoUsuario.create("CLIENTE");
        Endereco endereco = Endereco.create("Rua", 1, "Bairro", "Cidade", "Estado", "12345678");
        assertThrows(IllegalArgumentException.class, () -> Usuario.create(null, "email@teste.com", "login", "senha", tipoUsuario, endereco));
    }

    @Test
    void deveLancarExcecaoParaEmailInvalido() {
        TipoUsuario tipoUsuario = TipoUsuario.create("CLIENTE");
        Endereco endereco = Endereco.create("Rua", 1, "Bairro", "Cidade", "Estado", "12345678");
        assertThrows(IllegalArgumentException.class, () -> Usuario.create("Nome", "email-invalido", "login", "senha", tipoUsuario, endereco));
    }

    @Test
    void deveLancarExcecaoParaLoginInvalido() {
        TipoUsuario tipoUsuario = TipoUsuario.create("CLIENTE");
        Endereco endereco = Endereco.create("Rua", 1, "Bairro", "Cidade", "Estado", "12345678");
        assertThrows(IllegalArgumentException.class, () -> Usuario.create("Nome", "email@teste.com", null, "senha", tipoUsuario, endereco));
    }

    @Test
    void deveLancarExcecaoParaSenhaInvalida() {
        TipoUsuario tipoUsuario = TipoUsuario.create("CLIENTE");
        Endereco endereco = Endereco.create("Rua", 1, "Bairro", "Cidade", "Estado", "12345678");
        assertThrows(IllegalArgumentException.class, () -> Usuario.create("Nome", "email@teste.com", "login", null, tipoUsuario, endereco));
    }
}
