package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.UsuarioController;
import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UsuarioApiControllerTest {

    private UsuarioApiController usuarioApiController;

    @Mock
    private IUsuarioDataSource usuarioDataSource;

    @Mock
    private IRestauranteDataSource restauranteDataSource;

    @Mock
    private ITipoUsuarioDataSource tipoUsuarioDataSource;

    @Mock
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        usuarioApiController = new UsuarioApiController(usuarioDataSource, restauranteDataSource, tipoUsuarioDataSource);
        Field controllerField = UsuarioApiController.class.getDeclaredField("usuarioController");
        controllerField.setAccessible(true);
        controllerField.set(usuarioApiController, usuarioController);
    }

    @Test
    void buscarPorNome() {
        EnderecoDTO enderecoDTO = new EnderecoDTO("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        UsuarioDTO usuarioDTO = new UsuarioDTO("Test", "test@test.com", "test", "Admin", enderecoDTO);
        when(usuarioController.buscarPorNome("Test")).thenReturn(Collections.singletonList(usuarioDTO));

        ResponseEntity<List<UsuarioDTO>> response = usuarioApiController.buscarPorNome("Test");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void criarUsuario() {
        EnderecoDTO enderecoDTO = new EnderecoDTO("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        NovoUsuarioDTO novoUsuarioDTO = new NovoUsuarioDTO("Test", "test@test.com", "test", "password", "Admin", enderecoDTO);
        UsuarioDTO usuarioDTO = new UsuarioDTO("Test", "test@test.com", "test", "Admin", enderecoDTO);
        when(usuarioController.cadastrar(novoUsuarioDTO)).thenReturn(usuarioDTO);

        ResponseEntity<Void> response = usuarioApiController.criarUsuario(novoUsuarioDTO);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void deletarUsuario() {
        doNothing().when(usuarioController).deletarUsuario("test");

        ResponseEntity<Void> response = usuarioApiController.deletarUsuario("test");

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void updateUsuario() {
        EnderecoDTO enderecoDTO = new EnderecoDTO("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        UsuarioAlteracaoDTO usuarioAlteracaoDTO = new UsuarioAlteracaoDTO("Test", "test@test.com", "test", "Admin", enderecoDTO);
        UsuarioDTO usuarioDTO = new UsuarioDTO("Test", "test@test.com", "test", "Admin", enderecoDTO);
        when(usuarioController.alterarUsuario(usuarioAlteracaoDTO)).thenReturn(usuarioDTO);

        ResponseEntity<Void> response = usuarioApiController.updateUsuario(usuarioAlteracaoDTO);

        assertEquals(200, response.getStatusCode().value());
    }
}