package br.com.fiap.lunchtech.core.dto.usuario;

public record NovoUsuarioDTO(String nomeUsuario, String enderecoEmail, String login, String senha, String tipoDeUsuario) {
}
