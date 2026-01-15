package br.com.fiap.lunchtech.infra.database.repositories;

import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ITipoUsuarioRepositoryIT {

    @Autowired
    private ITipoUsuarioRepository tipoUsuarioRepository;

    @Test
    void deveSalvarTipoUsuario() {
        TipoUsuarioEntity tipoUsuario = TipoUsuarioEntity.builder()
                .tipoUsuario("CLIENTE")
                .build();

        TipoUsuarioEntity salvo = tipoUsuarioRepository.save(tipoUsuario);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getTipoUsuario()).isEqualTo("CLIENTE");
    }

    @Test
    void deveBuscarPorTipoUsuario() {
        TipoUsuarioEntity tipoUsuario = TipoUsuarioEntity.builder()
                .tipoUsuario("ADMIN")
                .build();
        tipoUsuarioRepository.save(tipoUsuario);

        TipoUsuarioEntity encontrado = tipoUsuarioRepository.findByTipoUsuario("ADMIN");

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getTipoUsuario()).isEqualTo("ADMIN");
    }
}
