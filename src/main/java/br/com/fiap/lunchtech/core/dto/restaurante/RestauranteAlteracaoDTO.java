package br.com.fiap.lunchtech.core.dto.restaurante;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;

import java.util.Date;

public record RestauranteAlteracaoDTO (String nomeRestaurante,
                                       String tipoCozinha,
                                       Date horarioFuncionamentoInicio,
                                       Date horarioFuncionamentoFim,
                                       EnderecoDTO endereco,
                                       UsuarioDonoRestauranteDTO donoRestaurante){
}
