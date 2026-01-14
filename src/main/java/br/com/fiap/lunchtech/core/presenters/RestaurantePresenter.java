package br.com.fiap.lunchtech.core.presenters;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.entities.Endereco;
import br.com.fiap.lunchtech.core.entities.Restaurante;
import br.com.fiap.lunchtech.core.entities.Usuario;

public class RestaurantePresenter {
    public static RestauranteDTO toDto(Restaurante restaurante) {
        if (restaurante == null) {
            return null;
        }

        EnderecoDTO enderecoDTO = restaurante.getEndereco() != null ? toEnderecoDto(restaurante.getEndereco()) : null;
        UsuarioDonoRestauranteDTO donoRestauranteDTO = restaurante.getDonoRestaurante() != null ? toDonoDto(restaurante.getDonoRestaurante()) : null;

        return new RestauranteDTO(restaurante.getId(),
                restaurante.getNome(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamentoInicio(),
                restaurante.getHorarioFuncionamentoFim(),
                enderecoDTO,
                donoRestauranteDTO);
    }

    private static EnderecoDTO toEnderecoDto(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        return new EnderecoDTO(endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep());
    }

    private static UsuarioDonoRestauranteDTO toDonoDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioDonoRestauranteDTO(usuario.getLogin(), usuario.getNome());
    }
}
