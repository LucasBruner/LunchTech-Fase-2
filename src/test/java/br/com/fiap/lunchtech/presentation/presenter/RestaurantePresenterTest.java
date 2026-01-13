package br.com.fiap.lunchtech.presentation.presenter;

import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.presenters.RestaurantePresenter;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantePresenterTest {

    @Test
    void deveConverterParaDto() {
        Endereco endereco = Endereco.create("Rua", 1, "Bairro", "Cidade", "Estado", "12345678");
        TipoUsuario tipoUsuario = TipoUsuario.create("DONO_RESTAURANTE");
        Usuario dono = Usuario.create("Dono", "dono@email.com", "dono_login", "senha", tipoUsuario, endereco);
        Restaurante restaurante = Restaurante.create("Restaurante", "Cozinha", new Date(), new Date(), endereco, dono);

        RestauranteDTO dto = RestaurantePresenter.toDto(restaurante);

        assertNotNull(dto);
        assertEquals("Restaurante", dto.nomeRestaurante());
        assertEquals("Cozinha", dto.tipoCozinha());
        assertNotNull(dto.donoRestaurante());
        assertEquals("dono_login", dto.donoRestaurante().login());
    }
}
