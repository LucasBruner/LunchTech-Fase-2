package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.UsuarioController;
import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.infra.http.controller.json.EnderecoJson;
import br.com.fiap.lunchtech.infra.http.controller.json.UsuarioJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
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
        UsuarioJson usuarioJson = new UsuarioJson();
        setField(usuarioJson, "nomeUsuario", "Test");
        setField(usuarioJson, "enderecoEmail", "test@test.com");
        setField(usuarioJson, "login", "test");
        setField(usuarioJson, "senha", "password");
        setField(usuarioJson, "tipoDeUsuario", "Admin");
        
        EnderecoJson enderecoJson = new EnderecoJson("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        setField(usuarioJson, "endereco", enderecoJson);

        EnderecoDTO enderecoDTO = new EnderecoDTO("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        UsuarioDTO usuarioDTO = new UsuarioDTO("Test", "test@test.com", "test", "Admin", enderecoDTO);
        when(usuarioController.cadastrar(any(NovoUsuarioDTO.class))).thenReturn(usuarioDTO);

        ResponseEntity<Void> response = usuarioApiController.criarUsuario(usuarioJson);

        assertEquals(200, response.getStatusCode().value());
        
        ArgumentCaptor<NovoUsuarioDTO> captor = ArgumentCaptor.forClass(NovoUsuarioDTO.class);
        verify(usuarioController).cadastrar(captor.capture());
        assertEquals("Test", captor.getValue().nomeUsuario());
    }

    @Test
    void deletarUsuario() {
        doNothing().when(usuarioController).deletarUsuario("test");

        ResponseEntity<Void> response = usuarioApiController.deletarUsuario("test");

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void updateUsuario() {
        UsuarioJson usuarioJson = new UsuarioJson();
        setField(usuarioJson, "nomeUsuario", "Test");
        setField(usuarioJson, "enderecoEmail", "test@test.com");
        setField(usuarioJson, "login", "test");
        setField(usuarioJson, "tipoDeUsuario", "Admin");
        
        EnderecoJson enderecoJson = new EnderecoJson("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        setField(usuarioJson, "endereco", enderecoJson);

        EnderecoDTO enderecoDTO = new EnderecoDTO("Rua X", 10, "Bairro", "Cidade", "Estado", "12345-678");
        UsuarioDTO usuarioDTO = new UsuarioDTO("Test", "test@test.com", "test", "Admin", enderecoDTO);
        when(usuarioController.alterarUsuario(any(UsuarioAlteracaoDTO.class))).thenReturn(usuarioDTO);

        ResponseEntity<Void> response = usuarioApiController.updateUsuario(usuarioJson);

        assertEquals(200, response.getStatusCode().value());
        
        ArgumentCaptor<UsuarioAlteracaoDTO> captor = ArgumentCaptor.forClass(UsuarioAlteracaoDTO.class);
        verify(usuarioController).alterarUsuario(captor.capture());
        assertEquals("Test", captor.getValue().nomeUsuario());
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}