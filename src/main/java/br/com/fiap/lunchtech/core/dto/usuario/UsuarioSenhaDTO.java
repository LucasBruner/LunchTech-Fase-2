package br.com.fiap.lunchtech.core.dto.usuario;

import java.time.LocalDateTime;

public record UsuarioSenhaDTO(String login,
                              String senha,
                              LocalDateTime dataAtualizacao) {
}
