package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.exceptions.RestauranteJaExistenteException;
import br.com.fiap.lunchtech.core.exceptions.RestauranteNaoEncontradoException;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.IRestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Component
public class RestauranteDataSource implements IRestauranteDataSource {
    private final IRestauranteRepository restauranteRepository;
    private final EnderecoDataSource enderecoDataSource;
    private final UsuarioDataSource usuarioDataSource;

    public RestauranteDataSource(IRestauranteRepository restauranteRepository,
                                 EnderecoDataSource enderecoDataSource,
                                 UsuarioDataSource usuarioDataSource) {
        this.restauranteRepository = restauranteRepository;
        this.enderecoDataSource = enderecoDataSource;
        this.usuarioDataSource = usuarioDataSource;
    }

    @Override
    public RestauranteDTO buscarRestaurantePorNome(String nomeRestaurante) {
        try {
            RestauranteEntity restaurante = restauranteRepository.findByNome(nomeRestaurante);

            if (restaurante == null) {
                throw new RestauranteNaoEncontradoException("Restaurante não encontrado!");
            }
            return entityToDtoRestaurante(restaurante);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Restaurante não encontrado!", e);
        }
    }

    @Override
    public void deletarRestaurante(String nomeRestaurante) {
        try {
            RestauranteEntity restaurante = restauranteRepository.findByNome(nomeRestaurante);
            restauranteRepository.delete(restaurante);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Restaurante não encontrado!", e);
        }
    }

    @Override
    public RestauranteDTO alterarRestaurante(RestauranteAlteracaoDTO restauranteAlteracaoDTO) {
        RestauranteEntity restauranteAlterado = new RestauranteEntity();
        UsuarioEntity donoRestauranteAlterado = buscarUsuarioPorLogin(restauranteAlteracaoDTO.donoRestaurante().login());

        restauranteAlterado.setId(restauranteAlteracaoDTO.idRestaurante());
        restauranteAlterado.setNome(restauranteAlteracaoDTO.nomeRestaurante());
        restauranteAlterado.setTipoCozinha(restauranteAlteracaoDTO.tipoCozinha());
        restauranteAlterado.setHorarioFuncionamentoInicio(restauranteAlteracaoDTO.horarioFuncionamentoInicio());
        restauranteAlterado.setHorarioFuncionamentoFim(restauranteAlteracaoDTO.horarioFuncionamentoFim());
        restauranteAlterado.setDonoRestaurante(donoRestauranteAlterado);

        EnderecoEntity enderecoAlterar = enderecoDataSource
                .updateFromRestaurante(restauranteAlteracaoDTO.endereco(),
                restauranteAlterado.getId());

        restauranteAlterado.setEndereco(enderecoAlterar);
        restauranteRepository.save(restauranteAlterado);

        EnderecoDTO enderecoDTO = restauranteEntityToEnderecoDTO(restauranteAlterado);
        UsuarioDonoRestauranteDTO donoRestauranteDTO = restauranteDonoToDTO(restauranteAlterado.getDonoRestaurante());
        return mapToDomainRestaurante(restauranteAlterado, enderecoDTO, donoRestauranteDTO);
    }

    @Override
    public RestauranteDTO incluirNovoRestaurante(NovoRestauranteDTO novoRestauranteDTO) {
        RestauranteEntity novoRestaurante = new RestauranteEntity();
        UsuarioEntity novoDonoRestaurante = buscarUsuarioPorLogin(novoRestauranteDTO.donoRestaurante().login());

        // Incluir endereço
        EnderecoEntity novoEndereco = enderecoDataSource.save(novoRestauranteDTO.endereco());

        // Incluir restaurante
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

    @Override
    public List<RestauranteDTO> buscarRestaurantesPorLogin(String login) {
        UsuarioEntity usuario = buscarUsuarioPorLogin(login);
        List<RestauranteEntity> restaurantes = usuario.getRestaurantes();

        if (restaurantes.isEmpty()) {
            throw new EntityNotFoundException("Restaurante não encontrado!");
        }
        UsuarioDonoRestauranteDTO  donoRestauranteDTO = entityToDonoDtoUsuario(usuario);

        return restaurantes.stream()
                .map(r -> mapToDomainRestaurante(r,
                        entityToDtoEndereco(r.getEndereco()),
                        donoRestauranteDTO))
                .toList();
    }

    @Override
    public RestauranteDTO buscarRestaurantePorId(Long id) {
        try {
            RestauranteEntity restaurante = restauranteRepository.findById(id).orElse(null);

            if (restaurante == null) {
                throw new RestauranteNaoEncontradoException("Restaurante não encontrado!");
            }

            return entityToDtoRestaurante(restaurante);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Restaurante não encontrado!", e);
        }
    }

    public Long buscarRestauranteID(String nomeRestaurante) {
        RestauranteEntity restaurante = restauranteRepository.findByNome(nomeRestaurante);
        return restaurante.getId();
    }

    public RestauranteEntity buscarRestauranteEntity(String nomeRestaurante) {
        return restauranteRepository.findByNome(nomeRestaurante);
    }

    private RestauranteDTO entityToDtoRestaurante(RestauranteEntity restaurante){
        return new RestauranteDTO(restaurante.getId(),
                restaurante.getNome(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamentoInicio(),
                restaurante.getHorarioFuncionamentoFim(),
                entityToDtoEndereco(restaurante.getEndereco()),
                entityToDonoDtoUsuario(restaurante.getDonoRestaurante()));
    }

    private EnderecoDTO entityToDtoEndereco(EnderecoEntity enderecoEntity){
        return enderecoDataSource.entityToDtoEndereco(enderecoEntity);
    }

    private EnderecoDTO restauranteEntityToEnderecoDTO(RestauranteEntity restauranteAlterar) {
        return enderecoDataSource.restauranteEntityToEnderecoDTO(restauranteAlterar);
    }

    private RestauranteDTO mapToDomainRestaurante(RestauranteEntity restauranteAlterar,
                                                  EnderecoDTO enderecoDTO,
                                                  UsuarioDonoRestauranteDTO donoRestauranteDTO) {
        return enderecoDataSource.mapToDomainRestaurante(restauranteAlterar, enderecoDTO, donoRestauranteDTO);
    }

    private UsuarioDonoRestauranteDTO entityToDonoDtoUsuario(UsuarioEntity usuario){
        return usuarioDataSource.entityToDonoDtoUsuario(usuario);
    }


    private UsuarioDonoRestauranteDTO restauranteDonoToDTO(UsuarioEntity donoRestaurante) {
        return usuarioDataSource.restauranteDonoToDTO(donoRestaurante);
    }

    private UsuarioEntity buscarUsuarioPorLogin(String login) {
        return usuarioDataSource.findByLogin(login);
    }
}
