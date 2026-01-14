package br.com.fiap.lunchtech.presentation.presenter;

import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.presenters.UsuarioPresenter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioPresenterTest {

    @Test
    void deveConverterParaDto() {
        Endereco endereco = Endereco.create("Rua", 1, "Bairro", "Cidade", "Estado", "12345678");
        TipoUsuario tipoUsuario = TipoUsuario.create("TIPO");
        Usuario usuario = Usuario.create("Nome", "email@teste.com", "login", "senha", tipoUsuario, endereco);

        UsuarioDTO dto = UsuarioPresenter.toDto(usuario);

        assertNotNull(dto);
        assertEquals("Nome", dto.nomeUsuario());
        assertEquals("email@teste.com", dto.enderecoEmail());
        assertEquals("login", dto.login());
        assertEquals("TIPO", dto.tipoDeUsuario());
        assertNotNull(dto.endereco());
    }

    @Test
    void deveConverterParaUsuarioAlteradoDto() {
        Usuario usuario = Usuario.create("login", "nova_senha");
        UsuarioAlteradoDTO dto = UsuarioPresenter.mostrarUsuarioAlterado(usuario);

        assertNotNull(dto);
        assertEquals("login", dto.login());
    }
}
