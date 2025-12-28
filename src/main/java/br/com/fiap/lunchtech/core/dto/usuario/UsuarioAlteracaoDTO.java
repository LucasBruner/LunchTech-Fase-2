package br.com.fiap.lunchtech.core.dto.usuario;

public record UsuarioAlteracaoDTO(String nomeUsuario,
                                  String enderecoEmail,
                                  String login,
                                  String tipoDeUsuario) {
}
