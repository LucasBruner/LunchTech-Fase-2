package br.com.fiap.lunchtech.core.dto.restaurante;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;

import java.time.LocalTime;

public record RestauranteAlteracaoDTO (Long idRestaurante,
                                       String nomeRestaurante,
                                       String tipoCozinha,
                                       LocalTime horarioFuncionamentoInicio,
                                       LocalTime horarioFuncionamentoFim,
                                       EnderecoDTO endereco,
                                       UsuarioDonoRestauranteDTO donoRestaurante){
}
