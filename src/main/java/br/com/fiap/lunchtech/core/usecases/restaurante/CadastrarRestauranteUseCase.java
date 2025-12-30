package br.com.fiap.lunchtech.core.usecases.restaurante;

import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

public class CadastrarRestauranteUseCase {
    private final IRestauranteGateway restauranteGateway;
    private final IUsuarioGateway usuarioGateway;

    private CadastrarRestauranteUseCase(IRestauranteGateway restauranteGateway, IUsuarioGateway usuarioGateway) {
        this.restauranteGateway = restauranteGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public static CadastrarRestauranteUseCase create(IRestauranteGateway restauranteGateway, IUsuarioGateway usuarioGateway) {
        return new CadastrarRestauranteUseCase(restauranteGateway, usuarioGateway);
    }

    public Restaurante run() {
        return null;
    }
}
