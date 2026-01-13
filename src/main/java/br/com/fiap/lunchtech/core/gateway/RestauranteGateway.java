package br.com.fiap.lunchtech.core.gateway;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.TipoUsuario;
import br.com.fiap.lunchtech.core.entities.Usuario;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteGateway;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;

import java.util.List;

public class RestauranteGateway implements IRestauranteGateway {
    private final IRestauranteDataSource restauranteDataSource;
    private IUsuarioDataSource usuarioDataSource;

    private RestauranteGateway(IRestauranteDataSource restauranteDataSource) {
        this.restauranteDataSource = restauranteDataSource;
    }

    private RestauranteGateway(IRestauranteDataSource restauranteDataSource,
                               IUsuarioDataSource usuarioDataSource) {
        this.restauranteDataSource = restauranteDataSource;
        this.usuarioDataSource = usuarioDataSource;
    }

    public static RestauranteGateway create(IRestauranteDataSource restauranteDataSource) {
        return new RestauranteGateway(restauranteDataSource);
    }

    public static RestauranteGateway create(IRestauranteDataSource restauranteDataSource,
                                            IUsuarioDataSource usuarioDataSource) {
        return new RestauranteGateway(restauranteDataSource, usuarioDataSource);
    }

    @Override
    public Restaurante alterar(Restaurante restauranteAlteracao) {
        RestauranteAlteracaoDTO restauranteAlteracaoDTO = mapearRestauranteAlteradoDTO(restauranteAlteracao);

        RestauranteDTO restauranteAlterado = this.restauranteDataSource.alterarRestaurante(restauranteAlteracaoDTO);

        Endereco enderecoRestaurante = mapearEnderecoRestaurante(restauranteAlterado.endereco());
        Usuario donoRestaurante = mapearDonoRestaurantParaUsuario(restauranteAlterado.donoRestaurante());

        return Restaurante.create(restauranteAlterado.id(),
                restauranteAlterado.nomeRestaurante(),
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

        return Restaurante.create(restauranteDTO.id(),
                restauranteDTO.nomeRestaurante(),
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
                mapearEnderecoRestauranteToDTO(restauranteAlteracao.getEndereco()),
                mapearDonoRestaurantParaDTO(restauranteAlteracao.getDonoRestaurante()));

        RestauranteDTO restauranteCriado = this.restauranteDataSource.incluirNovoRestaurante(novoRestauranteDTO);


        return Restaurante.create(restauranteCriado.id(),
                restauranteCriado.nomeRestaurante(),
                restauranteCriado.tipoCozinha(),
                restauranteCriado.horarioFuncionamentoInicio(),
                restauranteCriado.horarioFuncionamentoFim(),
                mapearEnderecoRestaurante(restauranteCriado.endereco()),
                mapearDonoRestaurantParaUsuario(restauranteCriado.donoRestaurante()));

        //validar se precisa do cardápio
    }

    @Override
    public List<Restaurante> buscarRestaurantesPorLogin(Usuario usuario) {
        List<RestauranteDTO> restaurantesDTO = this.restauranteDataSource.buscarRestaurantesPorLogin(usuario.getLogin());

        return restaurantesDTO.stream()
                .map(rdto -> Restaurante.create(
                        rdto.id(),
                        rdto.nomeRestaurante(),
                        rdto.tipoCozinha(),
                        rdto.horarioFuncionamentoInicio(),
                        rdto.horarioFuncionamentoFim(),
                        mapearEnderecoRestaurante(rdto.endereco()),
                        mapearDonoRestaurantParaUsuario(rdto.donoRestaurante())
                )).toList();
    }

    @Override
    public Restaurante buscarRestaurantePorId(Long id) {
        RestauranteDTO restauranteDTO = this.restauranteDataSource.buscarRestaurantePorId(id);

        if(restauranteDTO == null) {
            throw new RestauranteNaoEncontradoException("O restaurante desejado não foi encontrado.");
        }

        Endereco enderecoRestaurante = mapearEnderecoRestaurante(restauranteDTO.endereco());
        Usuario donoRestaurante = mapearDonoRestaurantParaUsuario(restauranteDTO.donoRestaurante());

        return Restaurante.create(restauranteDTO.id(),
                restauranteDTO.nomeRestaurante(),
                restauranteDTO.tipoCozinha(),
                restauranteDTO.horarioFuncionamentoInicio(),
                restauranteDTO.horarioFuncionamentoInicio(),
                enderecoRestaurante,
                donoRestaurante);
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
        UsuarioDTO usuario = usuarioDataSource.obterUsuarioPorLogin(usuarioDTO.login());
        TipoUsuario tipoUsuario = TipoUsuario.create(usuario.tipoDeUsuario());

        return Usuario.create(usuario.nomeUsuario(), usuario.enderecoEmail(), tipoUsuario);
    }

    private RestauranteAlteracaoDTO mapearRestauranteAlteradoDTO(Restaurante restauranteAlteracao) {
        EnderecoDTO enderecoDTO = mapearEnderecoRestauranteToDTO(restauranteAlteracao.getEndereco());
        UsuarioDonoRestauranteDTO donoRestauranteDTO = mapearDonoRestaurantParaDTO(restauranteAlteracao.getDonoRestaurante());

        return new RestauranteAlteracaoDTO(restauranteAlteracao.getId(),
                restauranteAlteracao.getNome(),
                restauranteAlteracao.getTipoCozinha(),
                restauranteAlteracao.getHorarioFuncionamentoInicio(),
                restauranteAlteracao.getHorarioFuncionamentoFim(),
                enderecoDTO,
                donoRestauranteDTO);
    }

    private UsuarioDonoRestauranteDTO mapearDonoRestaurantParaDTO(Usuario donoRestaurante) {
        return new UsuarioDonoRestauranteDTO(donoRestaurante.getLogin());
    }

    private EnderecoDTO mapearEnderecoRestauranteToDTO(Endereco endereco) {
        return new EnderecoDTO(endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep());
    }
}
