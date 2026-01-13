package br.com.fiap.lunchtech.core.dto.restaurante;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RestauranteCardapioDTO(String nomeRestaurante, Long id) {
}
