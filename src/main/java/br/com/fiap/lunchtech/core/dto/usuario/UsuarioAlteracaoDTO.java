package br.com.fiap.lunchtech.core.dto.usuario;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;

import java.time.LocalDateTime;

public record UsuarioAlteracaoDTO(String nomeUsuario,
                                  String enderecoEmail,
                                  String login,
                                  String tipoDeUsuario,
                                  EnderecoDTO endereco,
                                  LocalDateTime dataAtualizacao) {
}
