package br.com.fiap.lunchtech.infra.database.datasource;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.RestauranteEntity;
import br.com.fiap.lunchtech.infra.database.entities.UsuarioEntity;
import br.com.fiap.lunchtech.infra.database.repositories.IEnderecoRepository;
import org.springframework.stereotype.Component;

@Component
public class EnderecoDataSource {
    private final IEnderecoRepository enderecoRepository;

    public EnderecoDataSource(IEnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    EnderecoEntity save(EnderecoDTO endereco){
        EnderecoEntity enderecoSalvar = new EnderecoEntity();
        enderecoSalvar.setLogradouro(endereco.logradouro());
        enderecoSalvar.setBairro(endereco.bairro());
        enderecoSalvar.setCep(endereco.cep());
        enderecoSalvar.setNumero(endereco.numero());
        enderecoSalvar.setCidade(endereco.cidade());
        enderecoSalvar.setEstado(endereco.estado());

        enderecoRepository.save(enderecoSalvar);

        return enderecoSalvar;
    }

    EnderecoEntity updateFromUsuario(EnderecoDTO endereco, Long idUsuario){
        EnderecoEntity enderecoSalvar = enderecoRepository.findByUsuarioId(idUsuario);

        enderecoSalvar.setLogradouro(endereco.logradouro());
        enderecoSalvar.setBairro(endereco.bairro());
        enderecoSalvar.setCep(endereco.cep());
        enderecoSalvar.setNumero(endereco.numero());
        enderecoSalvar.setCidade(endereco.cidade());
        enderecoSalvar.setEstado(endereco.estado());

        enderecoRepository.save(enderecoSalvar);

        return enderecoSalvar;
    }

    EnderecoEntity updateFromRestaurante(EnderecoDTO endereco, Long idRestaurante){
        EnderecoEntity enderecoSalvar = enderecoRepository.findByRestauranteId(idRestaurante);

        enderecoSalvar.setLogradouro(endereco.logradouro());
        enderecoSalvar.setBairro(endereco.bairro());
        enderecoSalvar.setCep(endereco.cep());
        enderecoSalvar.setNumero(endereco.numero());
        enderecoSalvar.setCidade(endereco.cidade());
        enderecoSalvar.setEstado(endereco.estado());

        enderecoRepository.save(enderecoSalvar);

        return enderecoSalvar;
    }

    EnderecoDTO usuarioEntityToEnderecoDTO(UsuarioEntity usuario){
        return new EnderecoDTO(usuario.getEndereco().getLogradouro(),
                usuario.getEndereco().getNumero(),
                usuario.getEndereco().getBairro(),
                usuario.getEndereco().getCidade(),
                usuario.getEndereco().getEstado(),
                usuario.getEndereco().getCep());
    }

    EnderecoDTO entityToDtoEndereco(EnderecoEntity enderecoEntity){
        return new EnderecoDTO(enderecoEntity.getLogradouro(),
                enderecoEntity.getNumero(),
                enderecoEntity.getBairro(),
                enderecoEntity.getCidade(),
                enderecoEntity.getEstado(),
                enderecoEntity.getCep());
    }

    EnderecoDTO restauranteEntityToEnderecoDTO(RestauranteEntity restauranteAlterar) {
        return new EnderecoDTO(restauranteAlterar.getEndereco().getLogradouro(),
                restauranteAlterar.getEndereco().getNumero(),
                restauranteAlterar.getEndereco().getBairro(),
                restauranteAlterar.getEndereco().getCidade(),
                restauranteAlterar.getEndereco().getEstado(),
                restauranteAlterar.getEndereco().getCep());
    }

    RestauranteDTO mapToDomainRestaurante(RestauranteEntity restauranteAlterar,
                                          EnderecoDTO enderecoDTO,
                                          UsuarioDonoRestauranteDTO donoRestauranteDTO) {
        return new RestauranteDTO(restauranteAlterar.getNome(),
                restauranteAlterar.getTipoCozinha(),
                restauranteAlterar.getHorarioFuncionamentoInicio(),
                restauranteAlterar.getHorarioFuncionamentoFim(),
                enderecoDTO,
                donoRestauranteDTO);
    }
}
