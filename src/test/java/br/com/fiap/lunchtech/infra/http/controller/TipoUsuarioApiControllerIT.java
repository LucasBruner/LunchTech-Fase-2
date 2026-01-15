package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.ITipoUsuarioRepository;
import br.com.fiap.lunchtech.infra.http.controller.json.TipoUsuarioJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TipoUsuarioApiControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ITipoUsuarioRepository tipoUsuarioRepository;

    @BeforeEach
    void setUp() {
        tipoUsuarioRepository.deleteAll();
    }

    @Test
    void criarTipoUsuario() throws Exception {
        TipoUsuarioJson tipoUsuarioJson = new TipoUsuarioJson();
        setField(tipoUsuarioJson, "tipoUsuario", "ADMIN");

        mockMvc.perform(post("/v1/tipo-usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipoUsuarioJson)))
                .andExpect(status().isCreated());
    }

    @Test
    void buscarPorNome() throws Exception {
        tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "SUPERADMIN", null));

        mockMvc.perform(get("/v1/tipo-usuario/buscar")
                .param("tipo", "SUPERADMIN"))
                .andExpect(status().isOk());
    }

    @Test
    void updateTipoUsuario() throws Exception {
        tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "ADMIN", null));

        TipoUsuarioJson tipoUsuarioJson = new TipoUsuarioJson();
        setField(tipoUsuarioJson, "tipoUsuario", "ADMIN");
        setField(tipoUsuarioJson, "novoTipoUsuario", "SUPERADMIN");

        mockMvc.perform(put("/v1/tipo-usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tipoUsuarioJson)))
                .andExpect(status().isOk());
    }

    @Test
    void deletarTipoUsuario() throws Exception {
        tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "SUPERADMIN", null));

        mockMvc.perform(delete("/v1/tipo-usuario/{tipo}", "SUPERADMIN"))
                .andExpect(status().isOk());
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