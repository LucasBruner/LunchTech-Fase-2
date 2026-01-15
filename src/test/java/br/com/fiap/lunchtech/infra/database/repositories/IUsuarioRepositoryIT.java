package br.com.fiap.lunchtech.infra.database.repositories;

import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class IUsuarioRepositoryIT {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ITipoUsuarioRepository tipoUsuarioRepository;

    private TipoUsuarioEntity tipoUsuario;

    @BeforeEach
    void setup() {
        tipoUsuario = TipoUsuarioEntity.builder()
                .tipoUsuario("CLIENTE")
                .build();
        tipoUsuarioRepository.save(tipoUsuario);
    }

    @Test
    void deveSalvarUsuario() {
        UsuarioEntity usuario = UsuarioEntity.builder()
                .nome("João Silva")
                .login("joao.silva")
                .email("joao@email.com")
                .senha("123456")
                .tipoUsuario(tipoUsuario)
                .build();

        UsuarioEntity salvo = usuarioRepository.save(usuario);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getNome()).isEqualTo("João Silva");
    }

    @Test
    void deveBuscarPorLogin() {
        UsuarioEntity usuario = UsuarioEntity.builder()
                .nome("Maria")
                .login("maria.oliveira")
                .email("maria@email.com")
                .senha("123456")
                .tipoUsuario(tipoUsuario)
                .build();
        usuarioRepository.save(usuario);

        UsuarioEntity encontrado = usuarioRepository.findByLogin("maria.oliveira");

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getLogin()).isEqualTo("maria.oliveira");
    }

    @Test
    void deveBuscarPorEmail() {
        UsuarioEntity usuario = UsuarioEntity.builder()
                .nome("José")
                .login("jose.santos")
                .email("jose@email.com")
                .senha("123456")
                .tipoUsuario(tipoUsuario)
                .build();
        usuarioRepository.save(usuario);

        UsuarioEntity encontrado = usuarioRepository.findByEmail("jose@email.com");

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getEmail()).isEqualTo("jose@email.com");
    }

    @Test
    void deveBuscarPorNome() {
        UsuarioEntity usuario = UsuarioEntity.builder()
                .nome("Carlos")
                .login("carlos.souza")
                .email("carlos@email.com")
                .senha("123456")
                .tipoUsuario(tipoUsuario)
                .build();
        usuarioRepository.save(usuario);

        List<UsuarioEntity> encontrados = usuarioRepository.findByNome("Carlos");

        assertThat(encontrados).isNotEmpty();
        assertThat(encontrados.get(0).getNome()).isEqualTo("Carlos");
    }
}
