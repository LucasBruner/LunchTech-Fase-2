package br.com.fiap.lunchtech.core.dto.usuario;

public record UsuarioAutenticadoDTO(String nomeUsuario, String enderecoEmail, String login, String senha, String tipoDeUsuario) {
}
