package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.ITipoUsuarioRepository;
import br.com.fiap.lunchtech.infra.database.repositories.IUsuarioRepository;
import br.com.fiap.lunchtech.infra.http.controller.json.LoginJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class LoginUsuarioApiControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ITipoUsuarioRepository tipoUsuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
        tipoUsuarioRepository.deleteAll();
        tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "Admin", null));
        
        TipoUsuarioEntity tipoUsuario = tipoUsuarioRepository.findByTipoUsuario("Admin");
        EnderecoEntity endereco = new EnderecoEntity(null, "Rua X", "Bairro", "12345-678", 10, "Cidade", "Estado", null, null);
        UsuarioEntity usuario = new UsuarioEntity(null, "Test", "test", "password", "test@test.com", LocalDateTime.now(), tipoUsuario, endereco, null);
        usuarioRepository.save(usuario);
    }

    @Test
    void validarLogin() throws Exception {
        LoginJson loginJson = new LoginJson();
        setField(loginJson, "login", "test");
        setField(loginJson, "senha", "password");

        mockMvc.perform(post("/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginJson)))
                .andExpect(status().isOk());
    }

    @Test
    void alterarSenha() throws Exception {
        LoginJson loginJson = new LoginJson();
        setField(loginJson, "login", "test");
        setField(loginJson, "senha", "newpassword");

        mockMvc.perform(put("/v1/login/senha")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginJson)))
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