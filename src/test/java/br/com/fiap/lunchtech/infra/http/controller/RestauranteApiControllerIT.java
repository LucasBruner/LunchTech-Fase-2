package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.IRestauranteRepository;
import br.com.fiap.lunchtech.infra.database.repositories.ITipoUsuarioRepository;
import br.com.fiap.lunchtech.infra.database.repositories.IUsuarioRepository;
import br.com.fiap.lunchtech.infra.http.controller.json.EnderecoJson;
import br.com.fiap.lunchtech.infra.http.controller.json.RestauranteJson;
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
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class RestauranteApiControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Autowired
    private IRestauranteRepository restauranteRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ITipoUsuarioRepository tipoUsuarioRepository;

    @BeforeEach
    void setUp() {
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();
        tipoUsuarioRepository.deleteAll();
        tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "DONO_RESTAURANTE", null));
        
        TipoUsuarioEntity tipoUsuario = tipoUsuarioRepository.findByTipoUsuario("DONO_RESTAURANTE");
        EnderecoEntity endereco = new EnderecoEntity(null, "Rua X", "Bairro", "12345678", 10, "Cidade", "Estado", null, null);
        UsuarioEntity usuario = new UsuarioEntity(null, "Dono", "dono", "password", "dono@test.com", LocalDateTime.now(), tipoUsuario, endereco, null);
        usuarioRepository.save(usuario);
    }

    @Test
    void criarRestaurante() throws Exception {
        EnderecoJson enderecoJson = new EnderecoJson("Rua X", 10, "Bairro", "Cidade", "Estado", "12345678");
        RestauranteJson restauranteJson = new RestauranteJson();
        setField(restauranteJson, "nomeRestaurante", "Cantina da Nona");
        setField(restauranteJson, "tipoCozinha", "Italiana");
        setField(restauranteJson, "horarioFuncionamentoInicio", LocalTime.of(10, 0));
        setField(restauranteJson, "horarioFuncionamentoFim", LocalTime.of(22, 0));
        setField(restauranteJson, "endereco", enderecoJson);
        setField(restauranteJson, "login", "dono");

        mockMvc.perform(post("/v1/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restauranteJson)))
                .andExpect(status().isCreated());
    }

    @Test
    void buscarPorNome() throws Exception {
        UsuarioEntity dono = usuarioRepository.findByLogin("dono");
        EnderecoEntity endereco = new EnderecoEntity(null,
                "Rua X",
                "Bairro",
                "12345678",
                10,
                "Cidade",
                "Estado",
                null,
                null);
        RestauranteEntity restaurante = new RestauranteEntity(null,
                "Cantina da Nona",
                "Italiana",
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                dono,
                endereco,
                null);
        restauranteRepository.save(restaurante);

        mockMvc.perform(get("/v1/restaurantes/{nome}", "Cantina da Nona"))
                .andExpect(status().isOk());
    }

    @Test
    void alterarRestaurante() throws Exception {
        UsuarioEntity dono = usuarioRepository.findByLogin("dono");
        EnderecoEntity endereco = new EnderecoEntity(null,
                "Rua X",
                "Bairro",
                "12345678",
                10,
                "Cidade",
                "Estado",
                null,
                null);
        RestauranteEntity restaurante = new RestauranteEntity(null,
                "Cantina da Nona",
                "Italiana",
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                dono,
                endereco,
                null);
        restaurante = restauranteRepository.save(restaurante);

        EnderecoJson enderecoJson = new EnderecoJson("Rua X", 10, "Bairro", "Cidade", "Estado", "12345678");
        RestauranteJson restauranteJson = new RestauranteJson();
        setField(restauranteJson, "idRestaurante", restaurante.getId());
        setField(restauranteJson, "nomeRestaurante", "Cantina da Nona");
        setField(restauranteJson, "tipoCozinha", "Italiana");
        setField(restauranteJson, "horarioFuncionamentoInicio", LocalTime.of(10, 0));
        setField(restauranteJson, "horarioFuncionamentoFim", LocalTime.of(22, 0));
        setField(restauranteJson, "endereco", enderecoJson);
        setField(restauranteJson, "login", "dono");

        mockMvc.perform(put("/v1/restaurantes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restauranteJson)))
                .andExpect(status().isOk());
    }

    @Test
    void deletarRestaurante() throws Exception {
        UsuarioEntity dono = usuarioRepository.findByLogin("dono");
        EnderecoEntity endereco = new EnderecoEntity(null,
                "Rua X",
                "Bairro",
                "12345678",
                10,
                "Cidade",
                "Estado",
                null,
                null);
        RestauranteEntity restaurante = new RestauranteEntity(null,
                "Cantina da Nona",
                "Italiana",
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                dono,
                endereco,
                null);
        restauranteRepository.save(restaurante);

        mockMvc.perform(delete("/v1/restaurantes/{nome}", "Cantina da Nona"))
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