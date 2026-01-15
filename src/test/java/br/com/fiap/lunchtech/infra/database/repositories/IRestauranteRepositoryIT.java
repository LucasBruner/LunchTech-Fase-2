package br.com.fiap.lunchtech.infra.database.repositories;

import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class IRestauranteRepositoryIT {

    @Autowired
    private IRestauranteRepository restauranteRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ITipoUsuarioRepository tipoUsuarioRepository;

    private UsuarioEntity dono;

    @BeforeEach
    void setup() {
        TipoUsuarioEntity tipoUsuario = TipoUsuarioEntity.builder()
                .tipoUsuario("DONO_RESTAURANTE")
                .build();
        tipoUsuarioRepository.save(tipoUsuario);

        dono = UsuarioEntity.builder()
                .nome("Dono Teste")
                .login("dono.teste")
                .email("dono@email.com")
                .senha("123456")
                .tipoUsuario(tipoUsuario)
                .build();
        usuarioRepository.save(dono);
    }

    @Test
    void deveSalvarRestaurante() {
        RestauranteEntity restaurante = RestauranteEntity.builder()
                .nome("Restaurante do João")
                .tipoCozinha("Italiana")
                .horarioFuncionamentoInicio(LocalTime.of(10, 0))
                .horarioFuncionamentoFim(LocalTime.of(22, 0))
                .donoRestaurante(dono)
                .build();

        RestauranteEntity salvo = restauranteRepository.save(restaurante);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getNome()).isEqualTo("Restaurante do João");
    }

    @Test
    void deveBuscarPorNome() {
        RestauranteEntity restaurante = RestauranteEntity.builder()
                .nome("Pizzaria")
                .tipoCozinha("Pizza")
                .horarioFuncionamentoInicio(LocalTime.of(18, 0))
                .horarioFuncionamentoFim(LocalTime.of(23, 59))
                .donoRestaurante(dono)
                .build();
        restauranteRepository.save(restaurante);

        RestauranteEntity encontrado = restauranteRepository.findByNome("Pizzaria");

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNome()).isEqualTo("Pizzaria");
    }
}
