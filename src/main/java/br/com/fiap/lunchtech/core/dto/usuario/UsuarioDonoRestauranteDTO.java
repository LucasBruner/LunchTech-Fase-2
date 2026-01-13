package br.com.fiap.lunchtech.core.dto.usuario;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioDonoRestauranteDTO(
        String login,
        String nomeUsuario
) {}