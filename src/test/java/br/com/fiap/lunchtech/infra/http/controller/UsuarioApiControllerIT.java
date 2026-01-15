package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.ITipoUsuarioRepository;
import br.com.fiap.lunchtech.infra.database.repositories.IUsuarioRepository;
import br.com.fiap.lunchtech.infra.database.repositories.IRestauranteRepository;
import br.com.fiap.lunchtech.infra.http.controller.json.EnderecoJson;
import br.com.fiap.lunchtech.infra.http.controller.json.UsuarioJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UsuarioApiControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ITipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private IRestauranteRepository restauranteRepository;

    @BeforeEach
    void setUp() {
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();
        tipoUsuarioRepository.deleteAll();
        tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "Admin", null));
    }

    @Test
    void criarUsuario() throws Exception {
        UsuarioJson usuarioJson = new UsuarioJson();
        setField(usuarioJson, "nomeUsuario", "Test");
        setField(usuarioJson, "enderecoEmail", "test@test.com");
        setField(usuarioJson, "login", "test");
        setField(usuarioJson, "senha", "password");
        setField(usuarioJson, "tipoDeUsuario", "Admin");
        
        EnderecoJson enderecoJson = new EnderecoJson("Rua X", 10, "Bairro", "Cidade", "Estado", "12345678");
        setField(usuarioJson, "endereco", enderecoJson);

        mockMvc.perform(post("/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioJson)))
                .andExpect(status().isOk());
    }

    @Test
    void buscarPorNome() throws Exception {
        TipoUsuarioEntity tipoUsuario = tipoUsuarioRepository.findByTipoUsuario("Admin");
        EnderecoEntity endereco = new EnderecoEntity(null, "Rua X", "Bairro", "12345678", 10, "Cidade", "Estado", null, null);
        UsuarioEntity usuario = new UsuarioEntity(null, "Test", "test", "password", "test@test.com", LocalDateTime.now(), tipoUsuario, endereco, null);
        usuarioRepository.save(usuario);

        mockMvc.perform(get("/v1/usuarios")
                .param("nome", "Test"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUsuario() throws Exception {
        TipoUsuarioEntity tipoUsuario = tipoUsuarioRepository.findByTipoUsuario("Admin");
        EnderecoEntity endereco = new EnderecoEntity(null,
                "Rua X",
                "Bairro",
                "12345678",
                10,
                "Cidade",
                "Estado",
                null,
                null);
        UsuarioEntity usuario = new UsuarioEntity(null,
                "Test",
                "test",
                "password",
                "test@test.com",
                LocalDateTime.now(),
                tipoUsuario,
                endereco,
                null);
        usuarioRepository.save(usuario);

        UsuarioJson usuarioJson = new UsuarioJson();
        setField(usuarioJson, "nomeUsuario", "Test Updated");
        setField(usuarioJson, "enderecoEmail", "test2@test.com");
        setField(usuarioJson, "login", "test");
        setField(usuarioJson, "tipoDeUsuario", "Admin");
        
        EnderecoJson enderecoJson = new EnderecoJson("Rua X",
                10,
                "Bairro",
                "Cidade",
                "Estado",
                "12345678");
        setField(usuarioJson, "endereco", enderecoJson);

        mockMvc.perform(put("/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioJson)))
                .andExpect(status().isOk());
    }

    @Test
    void deletarUsuario() throws Exception {
        TipoUsuarioEntity tipoUsuario = tipoUsuarioRepository.findByTipoUsuario("Admin");
        EnderecoEntity endereco = new EnderecoEntity(null, "Rua X", "Bairro", "12345678", 10, "Cidade", "Estado", null, null);
        UsuarioEntity usuario = new UsuarioEntity(null, "Test", "test", "password", "test@test.com", LocalDateTime.now(), tipoUsuario, endereco, null);
        usuarioRepository.save(usuario);

        mockMvc.perform(delete("/v1/usuarios/{login}", "test"))
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