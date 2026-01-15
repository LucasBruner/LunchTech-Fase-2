package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.UsuarioController;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.infra.http.controller.json.LoginJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginUsuarioApiControllerTest {

    private LoginUsuarioApiController loginUsuarioApiController;

    @Mock
    private IUsuarioDataSource usuarioDataSource;

    @Mock
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        loginUsuarioApiController = new LoginUsuarioApiController(usuarioDataSource);
        Field controllerField = LoginUsuarioApiController.class.getDeclaredField("usuarioController");
        controllerField.setAccessible(true);
        controllerField.set(loginUsuarioApiController, usuarioController);
    }

    @Test
    void validarLogin() {
        LoginJson loginJson = new LoginJson();
        setField(loginJson, "login", "test");
        setField(loginJson, "senha", "password");

        when(usuarioController.validarLoginUsuario(any(UsuarioSenhaDTO.class))).thenReturn(true);

        ResponseEntity<Void> response = loginUsuarioApiController.validarLogin(loginJson);

        assertEquals(200, response.getStatusCode().value());
        
        ArgumentCaptor<UsuarioSenhaDTO> captor = ArgumentCaptor.forClass(UsuarioSenhaDTO.class);
        verify(usuarioController).validarLoginUsuario(captor.capture());
        assertEquals("test", captor.getValue().login());
    }

    @Test
    void alterarSenha() {
        LoginJson loginJson = new LoginJson();
        setField(loginJson, "login", "test");
        setField(loginJson, "senha", "newpassword");

        when(usuarioController.alterarSenhaUsuario(any(UsuarioSenhaDTO.class))).thenReturn(null);

        ResponseEntity<Void> response = loginUsuarioApiController.alterarSenha(loginJson);

        assertEquals(200, response.getStatusCode().value());
        
        ArgumentCaptor<UsuarioSenhaDTO> captor = ArgumentCaptor.forClass(UsuarioSenhaDTO.class);
        verify(usuarioController).alterarSenhaUsuario(captor.capture());
        assertEquals("test", captor.getValue().login());
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