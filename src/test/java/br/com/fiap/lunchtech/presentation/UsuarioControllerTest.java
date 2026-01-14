package br.com.fiap.lunchtech.presentation;

import br.com.fiap.lunchtech.core.controllers.UsuarioController;
import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    private UsuarioController usuarioController;

    @Mock
    private IUsuarioDataSource usuarioDataSource;
    @Mock
    private IRestauranteDataSource restauranteDataSource;
    @Mock
    private ITipoUsuarioDataSource tipoUsuarioDataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioController = UsuarioController.create(usuarioDataSource, restauranteDataSource, tipoUsuarioDataSource);
    }

    @Test
    void deveCadastrarUsuario() {
        // Arrange
        NovoUsuarioDTO novoUsuarioDTO = new NovoUsuarioDTO("nome", "email@test.com", "login", "senha", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));
        when(usuarioDataSource.incluirNovoUsuario(any(NovoUsuarioDTO.class))).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        when(tipoUsuarioDataSource.buscarTipoUsuarioPorNome(anyString())).thenReturn(new TipoUsuarioDTO("TIPO"));
        // Act
        UsuarioDTO result = usuarioController.cadastrar(novoUsuarioDTO);

        // Assert
        assertNotNull(result);
        assertEquals("login", result.login());
    }

    @Test
    void deveBuscarUsuarioPorNome() {
        // Arrange
        when(usuarioDataSource.buscarUsuariosPorNome("nome")).thenReturn(Arrays.asList(new UsuarioDTO("nome", "email@test.com", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"))));

        // Act
        List<UsuarioDTO> result = usuarioController.buscarPorNome("nome");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void deveBuscarTodosUsuarios() {
        // Arrange
        when(usuarioDataSource.buscarUsuarios()).thenReturn(Arrays.asList(new UsuarioDTO("nome", "email@test.com", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"))));

        // Act
        List<UsuarioDTO> result = usuarioController.buscarPorNome(null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void deveAlterarUsuario() {
        // Arrange
        UsuarioAlteracaoDTO usuarioAlteracaoDTO = new UsuarioAlteracaoDTO("nome", "email@test.com", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"));
        when(usuarioDataSource.obterUsuarioPorLogin(anyString())).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        when(usuarioDataSource.alterarUsuario(any(UsuarioAlteracaoDTO.class))).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        when(restauranteDataSource.buscarRestaurantePorNome("nome")).thenReturn(new RestauranteDTO(1L, "nome", "cozinha", new Date(), new Date(), new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000"), new UsuarioDonoRestauranteDTO("login", "nome")));
        when(tipoUsuarioDataSource.buscarTipoUsuarioPorNome(anyString())).thenReturn(new TipoUsuarioDTO("TIPO"));

        // Act
        UsuarioDTO result = usuarioController.alterarUsuario(usuarioAlteracaoDTO);

        // Assert
        assertNotNull(result);
        assertEquals("login", result.login());
    }

    @Test
    void deveDeletarUsuario() {
        // Arrange
        when(usuarioDataSource.obterUsuarioPorLogin(anyString())).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        when(restauranteDataSource.buscarRestaurantesPorLogin(anyString())).thenReturn(new ArrayList<>());
        doNothing().when(usuarioDataSource).deletarUsuario("login");

        // Act
        usuarioController.deletarUsuario("login");

        // Assert
        verify(usuarioDataSource, times(1)).deletarUsuario("login");
    }

    @Test
    void deveAlterarSenhaUsuario() {
        // Arrange
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO("login", "nova_senha");
        when(usuarioDataSource.obterUsuarioPorLogin("login")).thenReturn(new UsuarioDTO("nome", "email@test.com", "login", "TIPO", new EnderecoDTO("logradouro", 1, "bairro", "cidade", "estado", "01001000")));
        doNothing().when(usuarioDataSource).alterarSenhaUsuario(any(UsuarioSenhaDTO.class));

        // Act
        assertDoesNotThrow(() -> usuarioController.alterarSenhaUsuario(usuarioSenhaDTO));
    }

    @Test
    void deveValidarLoginUsuario() {
        // Arrange
        UsuarioSenhaDTO usuarioSenhaDTO = new UsuarioSenhaDTO("login", "senha");
        when(usuarioDataSource.buscarDadosUsuarioPorLogin("login")).thenReturn(new UsuarioSenhaDTO("login", "senha"));

        // Act
        boolean result = usuarioController.validarLoginUsuario(usuarioSenhaDTO);

        // Assert
        assertTrue(result);
    }
}
