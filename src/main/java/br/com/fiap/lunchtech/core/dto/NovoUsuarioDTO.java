package br.com.fiap.lunchtech.core.dto;

public record NovoUsuarioDTO(String nomeUsuario, String enderecoEmail, String login, String senha, String tipoDeUsuario) {
}
