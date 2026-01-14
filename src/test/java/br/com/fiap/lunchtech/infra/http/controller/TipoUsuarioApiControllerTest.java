package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.TipoUsuarioController;
import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.infra.http.controller.json.TipoUsuarioJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TipoUsuarioApiControllerTest {

    private TipoUsuarioApiController tipoUsuarioApiController;

    @Mock
    private ITipoUsuarioDataSource tipoUsuarioDataSource;

    @Mock
    private TipoUsuarioController tipoUsuarioController;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        tipoUsuarioApiController = new TipoUsuarioApiController(tipoUsuarioDataSource);
        Field controllerField = TipoUsuarioApiController.class.getDeclaredField("tipoUsuarioController");
        controllerField.setAccessible(true);
        controllerField.set(tipoUsuarioApiController, tipoUsuarioController);
    }

    @Test
    void buscarPorNome() {
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("Admin");
        when(tipoUsuarioController.buscarTipoUsuario(any(TipoUsuarioDTO.class))).thenReturn(Collections.singletonList(tipoUsuarioDTO));

        ResponseEntity<List<TipoUsuarioDTO>> response = tipoUsuarioApiController.buscarPorNome("Admin");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void criarTipoUsuario() {
        TipoUsuarioJson tipoUsuarioJson = new TipoUsuarioJson();
        setField(tipoUsuarioJson, "tipoUsuario", "Admin");

        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("Admin");
        when(tipoUsuarioController.cadastrarTipoUsuario(any(TipoUsuarioDTO.class))).thenReturn(tipoUsuarioDTO);

        ResponseEntity<Void> response = tipoUsuarioApiController.criarTipoUsuario(tipoUsuarioJson);

        assertEquals(201, response.getStatusCode().value());
        
        ArgumentCaptor<TipoUsuarioDTO> captor = ArgumentCaptor.forClass(TipoUsuarioDTO.class);
        verify(tipoUsuarioController).cadastrarTipoUsuario(captor.capture());
        assertEquals("Admin", captor.getValue().tipoUsuario());
    }

    @Test
    void deletarTipoUsuario() {
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("Admin");
        doNothing().when(tipoUsuarioController).deletarTipoUsuario(any(TipoUsuarioDTO.class));

        ResponseEntity<Void> response = tipoUsuarioApiController.deletarTipoUsuario("Admin");

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void updateTipoUsuario() {
        TipoUsuarioJson tipoUsuarioJson = new TipoUsuarioJson();
        setField(tipoUsuarioJson, "tipoUsuario", "Admin");
        setField(tipoUsuarioJson, "novoTipoUsuario", "SuperAdmin");

        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("SuperAdmin");
        when(tipoUsuarioController.alterarTipoUsuario(any(TipoUsuarioAlteracaoDTO.class))).thenReturn(tipoUsuarioDTO);

        ResponseEntity<Void> response = tipoUsuarioApiController.updateTipoUsuario(tipoUsuarioJson);

        assertEquals(200, response.getStatusCode().value());
        
        ArgumentCaptor<TipoUsuarioAlteracaoDTO> captor = ArgumentCaptor.forClass(TipoUsuarioAlteracaoDTO.class);
        verify(tipoUsuarioController).alterarTipoUsuario(captor.capture());
        assertEquals("Admin", captor.getValue().tipoUsuario());
        assertEquals("SuperAdmin", captor.getValue().novoTipoUsuario());
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