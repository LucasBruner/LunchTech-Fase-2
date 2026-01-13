package br.com.fiap.lunchtech.infra.config;

import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.ITipoUsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PopularDadosInicialTipoUsuario implements CommandLineRunner {

    private final ITipoUsuarioRepository tipoUsuarioRepository;

    public PopularDadosInicialTipoUsuario(ITipoUsuarioRepository tipoUsuarioRepository) {
        this.tipoUsuarioRepository = tipoUsuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(tipoUsuarioRepository.count() == 0) {
            tipoUsuarioRepository.saveAll(Arrays.asList(new TipoUsuarioEntity(null, "CLIENTE", null),
                    new TipoUsuarioEntity(null, "DONO_RESTAURANTE", null)
            ));
        }
    }
}
