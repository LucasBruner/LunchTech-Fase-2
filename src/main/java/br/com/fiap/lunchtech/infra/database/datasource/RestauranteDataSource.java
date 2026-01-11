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
import br.com.fiap.lunchtech.infra.database.repositories.IRestauranteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

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

        // Atualizar endereço
        EnderecoEntity enderecoAlterar =
                enderecoDataSource.updateFromRestaurante(restauranteAlteracaoDTO.endereco(), restauranteAlterado.getId());

        // Atualizar restaurante
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

    public Long buscarRestauranteID(String nomeRestaurante) {
        RestauranteEntity restaurante = restauranteRepository.findByName(nomeRestaurante);
        return restaurante.getId();
    }

    public RestauranteEntity buscarRestauranteEntity(String nomeRestaurante) {
        return restauranteRepository.findByName(nomeRestaurante);
    }

    private RestauranteDTO entityToDtoRestaurante(RestauranteEntity restaurante){
        return new RestauranteDTO(restaurante.getNome(),
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
