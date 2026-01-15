package br.com.fiap.lunchtech.infra.database.repositories;

import br.com.fiap.lunchtech.infra.database.entities.CardapioEntity;
import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ICardapioRepositoryIT {

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
    void setup() {
        TipoUsuarioEntity tipoUsuario = TipoUsuarioEntity.builder()
                .tipoUsuario("DONO_RESTAURANTE")
                .build();
        tipoUsuarioRepository.save(tipoUsuario);

        UsuarioEntity dono = UsuarioEntity.builder()
                .nome("Dono Teste")
                .login("dono.teste")
                .email("dono@email.com")
                .senha("123456")
                .tipoUsuario(tipoUsuario)
                .build();
        usuarioRepository.save(dono);

        restaurante = RestauranteEntity.builder()
                .nome("Restaurante Teste")
                .tipoCozinha("Variada")
                .horarioFuncionamentoInicio(LocalTime.of(11, 0))
                .horarioFuncionamentoFim(LocalTime.of(23, 0))
                .donoRestaurante(dono)
                .build();
        restauranteRepository.save(restaurante);
    }

    @Test
    void deveSalvarItemCardapio() {
        CardapioEntity item = CardapioEntity.builder()
                .nome("Prato Feito")
                .descricao("Arroz, feijão e bife")
                .preco(25.0)
                .apenasPresencial(false)
                .restaurante(restaurante)
                .build();

        CardapioEntity salvo = cardapioRepository.save(item);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getNome()).isEqualTo("Prato Feito");
    }

    @Test
    void deveBuscarPorNomeProdutoERestaurante() {
        CardapioEntity item = CardapioEntity.builder()
                .nome("Lasanha")
                .descricao("Lasanha à bolonhesa")
                .preco(45.0)
                .apenasPresencial(false)
                .restaurante(restaurante)
                .build();
        cardapioRepository.save(item);

        CardapioEntity encontrado = cardapioRepository.findByNomeProdutoAndRestauranteId("Lasanha", restaurante);

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNome()).isEqualTo("Lasanha");
        assertThat(encontrado.getRestaurante().getId()).isEqualTo(restaurante.getId());
    }

    @Test
    void deveListarTodosPorRestauranteId() {
        CardapioEntity item1 = CardapioEntity.builder()
                .nome("Item 1")
                .descricao("Desc 1")
                .preco(10.0)
                .restaurante(restaurante)
                .build();
        CardapioEntity item2 = CardapioEntity.builder()
                .nome("Item 2")
                .descricao("Desc 2")
                .preco(20.0)
                .restaurante(restaurante)
                .build();
        cardapioRepository.saveAll(List.of(item1, item2));

        List<CardapioEntity> itens = cardapioRepository.findAllByRestauranteId(restaurante.getId());

        assertThat(itens).hasSize(2);
    }
}
