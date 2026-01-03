package br.com.fiap.lunchtech.core.presenters;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.Usuario;

public class RestaurantePresenter {
    public static RestauranteDTO toDto(Restaurante novoRestaurante) {
        EnderecoDTO enderecoDTO = buscarEnderecoRestaurante(novoRestaurante.getEndereco());
        UsuarioDonoRestauranteDTO donoRestauranteDTO = buscarDonoRestaurante(novoRestaurante.getDonoRestaurante());

        return new RestauranteDTO(novoRestaurante.getNome(),
                novoRestaurante.getTipoCozinha(),
                novoRestaurante.getHorarioFuncionamentoInicio(),
                novoRestaurante.getHorarioFuncionamentoFim(),
                enderecoDTO,
                donoRestauranteDTO);
    }

    private static EnderecoDTO buscarEnderecoRestaurante(Endereco endereco) {
        return new EnderecoDTO(endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep());
    }

    private static UsuarioDonoRestauranteDTO buscarDonoRestaurante(Usuario usuario) {
        return new UsuarioDonoRestauranteDTO(usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipoDeUsuario().getTipoUsuario(),
                usuario.getLogin());
    }
}
