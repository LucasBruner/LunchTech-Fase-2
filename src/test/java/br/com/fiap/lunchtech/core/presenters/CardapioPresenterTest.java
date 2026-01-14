package br.com.fiap.lunchtech.core.presenters;

import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.entities.Cardapio;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CardapioPresenterTest {

    @Test
    void deveConverterParaDto() {
        Endereco endereco = Endereco.create("Rua", 1, "Bairro", "Cidade", "Estado", "12345678");
        TipoUsuario tipoUsuario = TipoUsuario.create("DONO_RESTAURANTE");
        Usuario dono = Usuario.create("Dono", "dono@email.com", "dono_login", "senha", tipoUsuario, endereco);
        Restaurante restaurante = Restaurante.create("Restaurante", "Cozinha", new Date(), new Date(), endereco, dono);
        Cardapio cardapio = Cardapio.create("Produto", "Desc", 10.0, false, "foto.jpg", restaurante);

        CardapioDTO dto = CardapioPresenter.toDto(cardapio);

        assertNotNull(dto);
        assertEquals("Produto", dto.nomeProduto());
        assertEquals(10.0, dto.preco());
        assertNotNull(dto.restauranteCardapioDTO());
        assertEquals("Restaurante", dto.restauranteCardapioDTO().nomeRestaurante());
    }
}
