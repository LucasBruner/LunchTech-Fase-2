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

    public RestauranteDataSource(IRestauranteRepository restauranteRepository, IEnderecoRepository enderecoRepository, IUsuarioRepository usuarioRepository) {
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
            throw new EntityNotFoundException("Restaurante não encontrado!");
        }
    }

    @Override
    public void deletarRestaurante(String nomeRestaurante) {
        try {
            RestauranteEntity restaurante = restauranteRepository.findByName(nomeRestaurante);
            enderecoRepository.deleteById(restaurante.getEndereco().getId());
            restauranteRepository.delete(restaurante);

            // ao deletar restaurante, devem ser deletados todos os itens do cardápio e endereço

        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Restaurante não encontrado!");
        }
    }

    @Override
    public RestauranteDTO alterarRestaurante(RestauranteAlteracaoDTO restauranteAlteracaoDTO) {
        return null;
    }

    @Override
    public RestauranteDTO incluirNovoRestaurante(NovoRestauranteDTO novoRestauranteDTO) {
        return null;
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
                enderecoEntity.getCep().toString());
    }

    private UsuarioDonoRestauranteDTO entityToDtoUsuario(UsuarioEntity usuario){
        return new UsuarioDonoRestauranteDTO(usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipoUsuario().getTipoUsuario(),
                usuario.getLogin());
    }
}
