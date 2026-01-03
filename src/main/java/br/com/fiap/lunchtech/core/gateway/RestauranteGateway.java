package br.com.fiap.lunchtech.core.gateway;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;

public class RestauranteGateway implements IRestauranteGateway {
    private final IRestauranteDataSource restauranteDataSource;

    private RestauranteGateway(IRestauranteDataSource restauranteDataSource) {
        this.restauranteDataSource = restauranteDataSource;
    }

    public static RestauranteGateway create(IRestauranteDataSource restauranteDataSource) {
        return new RestauranteGateway(restauranteDataSource);
    }

    @Override
    public Restaurante alterar(Restaurante restauranteAlteracao) {
        RestauranteAlteracaoDTO restauranteAlteracaoDTO = mapearRestauranteAlteradoDTO(restauranteAlteracao);

        RestauranteDTO restauranteAlterado = this.restauranteDataSource.alterarRestaurante(restauranteAlteracaoDTO);

        Endereco enderecoRestaurante = mapearEnderecoRestaurante(restauranteAlterado.endereco());
        Usuario donoRestaurante = mapearDonoRestaurantParaUsuario(restauranteAlterado.donoRestaurante());

        return Restaurante.create(restauranteAlterado.nomeRestaurante(),
                restauranteAlterado.tipoCozinha(),
                restauranteAlterado.horarioFuncionamentoInicio(),
                restauranteAlterado.horarioFuncionamentoFim(),
                enderecoRestaurante,
                donoRestaurante);
    }

    @Override
    public Restaurante buscarPorNome(String nomeRestaurante) {
        RestauranteDTO restauranteDTO = this.restauranteDataSource.buscarRestaurantePorNome(nomeRestaurante);

        if(restauranteDTO == null) {
            throw new RestauranteNaoEncontradoException("O restaurante desejado não foi encontrado.");
        }

        Endereco enderecoRestaurante = mapearEnderecoRestaurante(restauranteDTO.endereco());
        Usuario donoRestaurante = mapearDonoRestaurantParaUsuario(restauranteDTO.donoRestaurante());

        return Restaurante.create(restauranteDTO.nomeRestaurante(),
                restauranteDTO.tipoCozinha(),
                restauranteDTO.horarioFuncionamentoInicio(),
                restauranteDTO.horarioFuncionamentoInicio(),
                enderecoRestaurante,
                donoRestaurante);
    }

    @Override
    public void deletar(String nomeRestaurante) {
        this.restauranteDataSource.deletarRestaurante(nomeRestaurante);
    }

    @Override
    public Restaurante incluir(Restaurante restauranteAlteracao) {
        NovoRestauranteDTO novoRestauranteDTO = new NovoRestauranteDTO(
                restauranteAlteracao.getNome(),
                restauranteAlteracao.getTipoCozinha(),
                restauranteAlteracao.getHorarioFuncionamentoInicio(),
                restauranteAlteracao.getHorarioFuncionamentoFim(),
                mapearEnderecoRestaurantetoDTO(restauranteAlteracao.getEndereco()),
                null,
                mapearDonoRestaurantParaDTO(restauranteAlteracao.getDonoRestaurante()));

        RestauranteDTO restauranteCriado = this.restauranteDataSource.incluirNovoRestaurante(novoRestauranteDTO);


        return Restaurante.create(restauranteCriado.nomeRestaurante(),
                restauranteCriado.tipoCozinha(),
                restauranteCriado.horarioFuncionamentoInicio(),
                restauranteCriado.horarioFuncionamentoFim(),
                mapearEnderecoRestaurante(restauranteCriado.endereco()),
                mapearDonoRestaurantParaUsuario(restauranteCriado.donoRestaurante()));

        //validar se precisa do cardápio
    }

    private Endereco mapearEnderecoRestaurante(EnderecoDTO endereco) {
        return Endereco.create(endereco.logradouro(),
                endereco.numero(),
                endereco.bairro(),
                endereco.cidade(),
                endereco.estado(),
                endereco.cep());
    }

    private Usuario mapearDonoRestaurantParaUsuario(UsuarioDonoRestauranteDTO usuarioDTO) {
        var tipoUsuario = TipoUsuario.create(usuarioDTO.tipoDeUsuario());
        return Usuario.create(usuarioDTO.nomeUsuario(),
                usuarioDTO.enderecoEmail(),
                tipoUsuario);
    }

    private RestauranteAlteracaoDTO mapearRestauranteAlteradoDTO(Restaurante restauranteAlteracao) {
        EnderecoDTO enderecoDTO = mapearEnderecoRestaurantetoDTO(restauranteAlteracao.getEndereco());
        UsuarioDonoRestauranteDTO donoRestauranteDTO = mapearDonoRestaurantParaDTO(restauranteAlteracao.getDonoRestaurante());

        return new RestauranteAlteracaoDTO(restauranteAlteracao.getNome(),
                restauranteAlteracao.getTipoCozinha(),
                restauranteAlteracao.getHorarioFuncionamentoInicio(),
                restauranteAlteracao.getHorarioFuncionamentoFim(),
                enderecoDTO,
                donoRestauranteDTO);
    }

    private UsuarioDonoRestauranteDTO mapearDonoRestaurantParaDTO(Usuario donoRestaurante) {
        return new UsuarioDonoRestauranteDTO(donoRestaurante.getNome(),
                donoRestaurante.getEmail(),
                donoRestaurante.getTipoDeUsuario().getTipoUsuario(),
                donoRestaurante.getLogin());
    }

    private EnderecoDTO mapearEnderecoRestaurantetoDTO(Endereco endereco) {
        return new EnderecoDTO(endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep());
    }
}
