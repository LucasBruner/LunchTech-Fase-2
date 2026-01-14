package br.com.fiap.lunchtech.core.gateway;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioGatewayTest {

    private UsuarioGateway usuarioGateway;

    @Mock
    private IUsuarioDataSource dataSource;
    @Mock
    private TipoUsuarioGateway tipoUsuarioGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioGateway = UsuarioGateway.create(dataSource, tipoUsuarioGateway);
    }

    @Test
    void deveBuscarPorLoginExistente() {
        when(dataSource.obterUsuarioPorLogin("login")).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome(anyString())).thenReturn(mock(TipoUsuario.class));
        Usuario result = usuarioGateway.buscarPorLoginExistente("login");
        assertNotNull(result);
    }

    @Test
    void deveRetornarNuloSeLoginNaoExistir() {
        when(dataSource.obterUsuarioPorLogin("login_invalido")).thenReturn(null);
        Usuario result = usuarioGateway.buscarPorLoginExistente("login_invalido");
        assertNull(result);
    }

    @Test
    void deveBuscarPorEmail() {
        when(dataSource.buscarUsuarioPorEmail("email@teste.com")).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        assertTrue(usuarioGateway.buscarPorEmail("email@teste.com"));
    }

    @Test
    void naoDeveEncontrarEmailInexistente() {
        when(dataSource.buscarUsuarioPorEmail("email_invalido@teste.com")).thenReturn(null);
        assertFalse(usuarioGateway.buscarPorEmail("email_invalido@teste.com"));
    }

    @Test
    void deveIncluirUsuario() {
        Usuario usuario = mock(Usuario.class);
        when(usuario.getEndereco()).thenReturn(mock(Endereco.class));
        when(usuario.getTipoDeUsuario()).thenReturn(mock(TipoUsuario.class));
        when(dataSource.incluirNovoUsuario(any(NovoUsuarioDTO.class))).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        when(tipoUsuarioGateway.buscarTipoUsuarioPorNome(anyString())).thenReturn(mock(TipoUsuario.class));
        Usuario result = usuarioGateway.incluir(usuario);
        assertNotNull(result);
        verify(dataSource, times(1)).incluirNovoUsuario(any(NovoUsuarioDTO.class));
    }
}
