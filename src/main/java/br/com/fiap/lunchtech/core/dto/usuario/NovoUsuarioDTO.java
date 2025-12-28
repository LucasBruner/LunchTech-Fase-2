package br.com.fiap.lunchtech.core.dto.usuario;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;

public record NovoUsuarioDTO(String nomeUsuario,
                             String enderecoEmail,
                             String login,
                             String senha,
                             String tipoDeUsuario,
                             EnderecoDTO endereco) {
}
