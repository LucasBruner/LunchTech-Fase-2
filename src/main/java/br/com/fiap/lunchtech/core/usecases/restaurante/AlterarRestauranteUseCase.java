package br.com.fiap.lunchtech.core.usecases.restaurante;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.RestauranteEncontradoException;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

public class AlterarRestauranteUseCase {
    private final IRestauranteGateway restauranteGateway;
    private final IUsuarioGateway usuarioGateway;

    private AlterarRestauranteUseCase(IRestauranteGateway restauranteGateway,
                                      IUsuarioGateway usuarioGateway) {
        this.restauranteGateway = restauranteGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public static AlterarRestauranteUseCase create(IRestauranteGateway restauranteGateway,
                                                   IUsuarioGateway usuarioGateway) {
        return new AlterarRestauranteUseCase(restauranteGateway, usuarioGateway);
    }

    public Restaurante run(RestauranteAlteracaoDTO restauranteAlteracaoDTO) {
        restauranteGateway.buscarRestaurantePorId(restauranteAlteracaoDTO.idRestaurante());
        Restaurante restauranteExiste;
        try{
            restauranteExiste = restauranteGateway.buscarPorNome(restauranteAlteracaoDTO.nomeRestaurante());
        } catch (RestauranteNaoEncontradoException _) {
            restauranteExiste = null;
        }

        if(restauranteExiste != null
                && !restauranteExiste.getNome().equals(restauranteAlteracaoDTO.nomeRestaurante())) {
            throw new RestauranteEncontradoException("Já existe um restaurante com esse nome.");
        }

        Usuario donoRestaurante = buscarDonoRestaurante(restauranteAlteracaoDTO.donoRestaurante().login());
        Endereco enderecoRestaurante = buscarEndereco(restauranteAlteracaoDTO.endereco());

        Restaurante restauranteAlteracao = Restaurante.create(restauranteAlteracaoDTO.idRestaurante(),
                restauranteAlteracaoDTO.nomeRestaurante(),
                restauranteAlteracaoDTO.tipoCozinha(),
                restauranteAlteracaoDTO.horarioFuncionamentoInicio(),
                restauranteAlteracaoDTO.horarioFuncionamentoFim(),
                enderecoRestaurante,
                donoRestaurante);

        Restaurante restaurante = restauranteGateway.alterar(restauranteAlteracao);

        return restaurante;
    }

    private Endereco buscarEndereco(EnderecoDTO endereco) {
        return Endereco.create(endereco.logradouro(),
                endereco.numero(),
                endereco.bairro(),
                endereco.cidade(),
                endereco.estado(),
                endereco.cep()
        );
    }

    private Usuario buscarDonoRestaurante(String login) {
        return usuarioGateway.buscarPorLogin(login);
    }
}
