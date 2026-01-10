package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.IEnderecoRepository;
import br.com.fiap.lunchtech.infra.database.repositories.IRestauranteRepository;
import br.com.fiap.lunchtech.infra.database.repositories.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDataSource implements IRestauranteDataSource {
    private final IRestauranteRepository restauranteRepository;
    private final IEnderecoRepository enderecoRepository;
    private final IUsuarioRepository usuarioRepository;

    public RestauranteDataSource(IRestauranteRepository restauranteRepository,
                                 IEnderecoRepository enderecoRepository,
                                 IUsuarioRepository usuarioRepository) {
        this.restauranteRepository = restauranteRepository;
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public RestauranteDTO buscarRestaurantePorNome(String nomeRestaurante) {
        try {
            RestauranteEntity restaurante = restauranteRepository.findByName(nomeRestaurante);
            return entityToDtoRestaurante(restaurante);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Restaurante não encontrado!", e);
        }
    }

    @Override
    public void deletarRestaurante(String nomeRestaurante) {
        try {
            RestauranteEntity restaurante = restauranteRepository.findByName(nomeRestaurante);
            restauranteRepository.delete(restaurante);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Restaurante não encontrado!", e);
        }
    }

    @Override
    public RestauranteDTO alterarRestaurante(RestauranteAlteracaoDTO restauranteAlteracaoDTO) {
        RestauranteEntity restauranteAlterado = restauranteRepository.findByName(restauranteAlteracaoDTO.nomeRestaurante());
        UsuarioEntity donoRestauranteAlterado = buscarUsuarioPorLogin(restauranteAlteracaoDTO.donoRestaurante().login());

        EnderecoEntity enderecoAlterar = getEnderecoEntity(restauranteAlteracaoDTO, restauranteAlterado);
        enderecoRepository.save(enderecoAlterar);

        restauranteAlterado.setNome(restauranteAlteracaoDTO.nomeRestaurante());
        restauranteAlterado.setTipoCozinha(restauranteAlteracaoDTO.tipoCozinha());
        restauranteAlterado.setHorarioFuncionamentoInicio(restauranteAlteracaoDTO.horarioFuncionamentoInicio());
        restauranteAlterado.setHorarioFuncionamentoFim(restauranteAlteracaoDTO.horarioFuncionamentoFim());
        restauranteAlterado.setDonoRestaurante(donoRestauranteAlterado);
        restauranteAlterado.setEndereco(enderecoAlterar);
        restauranteRepository.save(restauranteAlterado);

        EnderecoDTO enderecoDTO = restauranteEntityToEnderecoDTO(restauranteAlterado);
        UsuarioDonoRestauranteDTO donoRestauranteDTO = restauranteDonoToDTO(restauranteAlterado.getDonoRestaurante());
        return mapToDomainRestaurante(restauranteAlterado, enderecoDTO, donoRestauranteDTO);
    }

    @Override
    public RestauranteDTO incluirNovoRestaurante(NovoRestauranteDTO novoRestauranteDTO) {
        RestauranteEntity novoRestaurante = new RestauranteEntity();
        EnderecoEntity novoEndereco = new EnderecoEntity();
        UsuarioEntity novoDonoRestaurante = buscarUsuarioPorLogin(novoRestauranteDTO.donoRestaurante().login());

        novoEndereco.setLogradouro(novoRestauranteDTO.endereco().logradouro());
        novoEndereco.setBairro(novoRestauranteDTO.endereco().bairro());
        novoEndereco.setCep(novoRestauranteDTO.endereco().cep());
        novoEndereco.setNumero(novoRestauranteDTO.endereco().numero());
        novoEndereco.setCidade(novoRestauranteDTO.endereco().cidade());
        novoEndereco.setEstado(novoRestauranteDTO.endereco().estado());

        enderecoRepository.save(novoEndereco);

        novoRestaurante.setNome(novoRestauranteDTO.nomeRestaurante());
        novoRestaurante.setTipoCozinha(novoRestauranteDTO.tipoCozinha());
        novoRestaurante.setHorarioFuncionamentoInicio(novoRestauranteDTO.horarioFuncionamentoInicio());
        novoRestaurante.setHorarioFuncionamentoFim(novoRestauranteDTO.horarioFuncionamentoFim());
        novoRestaurante.setEndereco(novoEndereco);
        novoRestaurante.setDonoRestaurante(novoDonoRestaurante);

        restauranteRepository.save(novoRestaurante);

        EnderecoDTO enderecoDTO = restauranteEntityToEnderecoDTO(novoRestaurante);
        UsuarioDonoRestauranteDTO donoRestauranteDTO = restauranteDonoToDTO(novoRestaurante.getDonoRestaurante());
        return mapToDomainRestaurante(novoRestaurante, enderecoDTO, donoRestauranteDTO);
    }

    private RestauranteDTO entityToDtoRestaurante(RestauranteEntity restaurante){
        return new RestauranteDTO(restaurante.getNome(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamentoInicio(),
                restaurante.getHorarioFuncionamentoFim(),
                entityToDtoEndereco(restaurante.getEndereco()),
                entityToDtoUsuario(restaurante.getDonoRestaurante()));
    }

    private EnderecoDTO entityToDtoEndereco(EnderecoEntity enderecoEntity){
        return new EnderecoDTO(enderecoEntity.getLogradouro(),
                enderecoEntity.getNumero(),
                enderecoEntity.getBairro(),
                enderecoEntity.getCidade(),
                enderecoEntity.getEstado(),
                enderecoEntity.getCep());
    }

    private UsuarioDonoRestauranteDTO entityToDtoUsuario(UsuarioEntity usuario){
        return new UsuarioDonoRestauranteDTO(usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipoUsuario().getTipoUsuario(),
                usuario.getLogin());
    }


    private static EnderecoEntity getEnderecoEntity(RestauranteAlteracaoDTO restauranteAlteracaoDTO, RestauranteEntity restauranteAlterado) {
        EnderecoEntity enderecoAlterar = restauranteAlterado.getEndereco();
        enderecoAlterar.setLogradouro(restauranteAlteracaoDTO.endereco().logradouro());
        enderecoAlterar.setBairro(restauranteAlteracaoDTO.endereco().bairro());
        enderecoAlterar.setCep(restauranteAlteracaoDTO.endereco().cep());
        enderecoAlterar.setNumero(restauranteAlteracaoDTO.endereco().numero());
        enderecoAlterar.setCidade(restauranteAlteracaoDTO.endereco().cidade());
        enderecoAlterar.setEstado(restauranteAlteracaoDTO.endereco().estado());
        return enderecoAlterar;
    }

    private EnderecoDTO restauranteEntityToEnderecoDTO(RestauranteEntity restauranteAlterar) {
        return new EnderecoDTO(restauranteAlterar.getEndereco().getLogradouro(),
                restauranteAlterar.getEndereco().getNumero(),
                restauranteAlterar.getEndereco().getBairro(),
                restauranteAlterar.getEndereco().getCidade(),
                restauranteAlterar.getEndereco().getEstado(),
                restauranteAlterar.getEndereco().getCep());
    }


    private UsuarioDonoRestauranteDTO restauranteDonoToDTO(UsuarioEntity donoRestaurante) {
        return new UsuarioDonoRestauranteDTO(donoRestaurante.getNome(),
                donoRestaurante.getEmail(),
                donoRestaurante.getTipoUsuario().toString(),
                donoRestaurante.getLogin());
    }

    private RestauranteDTO mapToDomainRestaurante(RestauranteEntity restauranteAlterar,
                                                  EnderecoDTO enderecoDTO,
                                                  UsuarioDonoRestauranteDTO donoRestauranteDTO) {
        return new RestauranteDTO(restauranteAlterar.getNome(),
                restauranteAlterar.getTipoCozinha(),
                restauranteAlterar.getHorarioFuncionamentoInicio(),
                restauranteAlterar.getHorarioFuncionamentoFim(),
                enderecoDTO,
                donoRestauranteDTO);
    }

    private UsuarioEntity buscarUsuarioPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public Long buscarRestauranteID(String nomeRestaurante) {
        RestauranteEntity restaurante = restauranteRepository.findByName(nomeRestaurante);
        return restaurante.getId();
    }

    public RestauranteEntity buscarRestauranteEntity(String nomeRestaurante) {
        return restauranteRepository.findByName(nomeRestaurante);
    }
}
