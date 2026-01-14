package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.TipoUsuarioController;
import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
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
        when(tipoUsuarioController.buscarTipoUsuario(tipoUsuarioDTO)).thenReturn(Collections.singletonList(tipoUsuarioDTO));

        ResponseEntity<List<TipoUsuarioDTO>> response = tipoUsuarioApiController.buscarPorNome("Admin");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void criarTipoUsuario() {
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("Admin");
        when(tipoUsuarioController.cadastrarTipoUsuario(tipoUsuarioDTO)).thenReturn(tipoUsuarioDTO);

        ResponseEntity<Void> response = tipoUsuarioApiController.criarTipoUsuario(tipoUsuarioDTO);

        assertEquals(201, response.getStatusCode().value());
    }

    @Test
    void deletarTipoUsuario() {
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("Admin");
        doNothing().when(tipoUsuarioController).deletarTipoUsuario(tipoUsuarioDTO);

        ResponseEntity<Void> response = tipoUsuarioApiController.deletarTipoUsuario("Admin");

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void updateTipoUsuario() {
        TipoUsuarioAlteracaoDTO tipoUsuarioAlteracaoDTO = new TipoUsuarioAlteracaoDTO("Admin", "SuperAdmin");
        TipoUsuarioDTO tipoUsuarioDTO = new TipoUsuarioDTO("SuperAdmin");
        when(tipoUsuarioController.alterarTipoUsuario(tipoUsuarioAlteracaoDTO)).thenReturn(tipoUsuarioDTO);

        ResponseEntity<Void> response = tipoUsuarioApiController.updateTipoUsuario(tipoUsuarioAlteracaoDTO);

        assertEquals(200, response.getStatusCode().value());
    }
}