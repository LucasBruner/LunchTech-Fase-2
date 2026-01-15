package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.infra.database.entities.CardapioEntity;
import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.ICardapioRepository;
import br.com.fiap.lunchtech.infra.database.repositories.IRestauranteRepository;
import br.com.fiap.lunchtech.infra.database.repositories.ITipoUsuarioRepository;
import br.com.fiap.lunchtech.infra.database.repositories.IUsuarioRepository;
import br.com.fiap.lunchtech.infra.http.controller.json.CardapioItemJson;
import br.com.fiap.lunchtech.infra.http.controller.json.CardapioJson;
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
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CardapioApiControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ICardapioRepository cardapioRepository;

    @Autowired
    private IRestauranteRepository restauranteRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ITipoUsuarioRepository tipoUsuarioRepository;

    private RestauranteEntity restaurante;

    @BeforeEach
    void setUp() {
        cardapioRepository.deleteAll();
        restauranteRepository.deleteAll();
        usuarioRepository.deleteAll();
        tipoUsuarioRepository.deleteAll();
        
        tipoUsuarioRepository.save(new TipoUsuarioEntity(null, "DONO_RESTAURANTE", null));
        TipoUsuarioEntity tipoUsuario = tipoUsuarioRepository.findByTipoUsuario("DONO_RESTAURANTE");
        
        EnderecoEntity endereco = new EnderecoEntity(null,
                "Rua X",
                "Bairro",
                "10004020",
                10,
                "Cidade",
                "Estado",
                null,
                null);
        UsuarioEntity dono = new UsuarioEntity(null,
                "Dono",
                "dono",
                "password",
                "dono@test.com",
                LocalDateTime.now(),
                tipoUsuario,
                endereco,
                null);
        usuarioRepository.save(dono);

        restaurante = new RestauranteEntity(null,
                "Restaurante",
                "Italiana",
                LocalTime.of(10, 0),
                LocalTime.of(22, 0),
                dono,
                endereco,
                null);
        restaurante = restauranteRepository.save(restaurante);
    }

    @Test
    void criarItemCardapio() throws Exception {
        CardapioJson cardapioJson = new CardapioJson();
        setField(cardapioJson, "nomeProduto", "Pizza");
        setField(cardapioJson, "descricao", "Calabresa");
        setField(cardapioJson, "preco", 30.0);
        setField(cardapioJson, "apenasPresencial", false);
        setField(cardapioJson, "fotoPrato", "foto.jpg");
        setField(cardapioJson, "restauranteId", restaurante.getId());

        mockMvc.perform(post("/v1/cardapio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cardapioJson)))
                .andExpect(status().isCreated());
    }

    @Test
    void buscarPorNome() throws Exception {
        CardapioEntity cardapio = new CardapioEntity(null,
                "Pizza",
                "Calabresa",
                30.0,
                false,
                "foto.jpg",
                restaurante);
        cardapioRepository.save(cardapio);

        CardapioItemJson cardapioItemJson = new CardapioItemJson();
        setField(cardapioItemJson, "nomeProduto", "Pizza");
        setField(cardapioItemJson, "nomeRestaurante", "Restaurante");

        mockMvc.perform(get("/v1/cardapio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cardapioItemJson)))
                .andExpect(status().isOk());
    }

    @Test
    void alterarRestaurante() throws Exception {
        CardapioEntity cardapio = new CardapioEntity(null, "Pizza", "Calabresa", 30.0, false, "foto.jpg", restaurante);
        cardapio = cardapioRepository.save(cardapio);

        CardapioJson cardapioJson = new CardapioJson();
        setField(cardapioJson, "id", cardapio.getId());
        setField(cardapioJson, "nomeProduto", "Pizza");
        setField(cardapioJson, "descricao", "Calabresa");
        setField(cardapioJson, "preco", 35.0);
        setField(cardapioJson, "apenasPresencial", false);
        setField(cardapioJson, "fotoPrato", "foto.jpg");
        setField(cardapioJson, "restauranteId", restaurante.getId());

        mockMvc.perform(put("/v1/cardapio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cardapioJson)))
                .andExpect(status().isOk());
    }

    @Test
    void deletarRestaurante() throws Exception {
        CardapioEntity cardapio = new CardapioEntity(null, "Pizza", "Calabresa", 30.0, false, "foto.jpg", restaurante);
        cardapio = cardapioRepository.save(cardapio);

        mockMvc.perform(delete("/v1/cardapio/{id}", cardapio.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void buscarCardapioRestaurante() throws Exception {
        CardapioEntity cardapio = new CardapioEntity(null, "Pizza", "Calabresa", 30.0, false, "foto.jpg", restaurante);
        cardapioRepository.save(cardapio);

        mockMvc.perform(get("/v1/cardapio/restaurante/{nome}", "Restaurante"))
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