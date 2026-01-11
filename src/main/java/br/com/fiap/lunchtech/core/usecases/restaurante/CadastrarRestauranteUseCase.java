package br.com.fiap.lunchtech.core.usecases.restaurante;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.RestauranteJaExistenteException;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioGateway;

public class CadastrarRestauranteUseCase {
    private final IRestauranteGateway restauranteGateway;
    private final IUsuarioGateway usuarioGateway;

    private CadastrarRestauranteUseCase(IRestauranteGateway restauranteGateway, IUsuarioGateway usuarioGateway) {
        this.restauranteGateway = restauranteGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public static CadastrarRestauranteUseCase create(IRestauranteGateway restauranteGateway,
                                                     IUsuarioGateway usuarioGateway) {
        return new CadastrarRestauranteUseCase(restauranteGateway, usuarioGateway);
    }

    public Restaurante run(NovoRestauranteDTO novoRestauranteDTO) {
        Restaurante restauranteExistente;
        try {
            restauranteExistente = restauranteGateway.buscarPorNome(novoRestauranteDTO.nomeRestaurante());
        } catch (RestauranteNaoEncontradoException _) {
            restauranteExistente = null;
        }

        if (restauranteExistente != null) {
            throw new RestauranteJaExistenteException("Restaurante já existe!");
        }

        Usuario donoRestaurante = buscarDonoRestaurante(novoRestauranteDTO.donoRestaurante().login());
        Endereco enderecoRestaurante = criarNovoEndereco(novoRestauranteDTO.endereco());

        Restaurante restauranteAlteracao = Restaurante.create(novoRestauranteDTO.nomeRestaurante(),
                novoRestauranteDTO.tipoCozinha(),
                novoRestauranteDTO.horarioFuncionamentoInicio(),
                novoRestauranteDTO.horarioFuncionamentoFim(),
                enderecoRestaurante,
                donoRestaurante);

        //Validar se o restaurante pode ser criado sem um cardápio inicial ou se precisa ser criado junto a um cardápio

        Restaurante restaurante = restauranteGateway.incluir(restauranteAlteracao);

        return restaurante;
    }

    private Endereco criarNovoEndereco(EnderecoDTO endereco) {
        return Endereco.create(endereco.logradouro(),
                endereco.numero(),
                endereco.bairro(),
                endereco.cidade(),
                endereco.estado(),
                endereco.cep());
    }

    private Usuario buscarDonoRestaurante(String login) {
        return usuarioGateway.buscarPorLogin(login);
    }
}
