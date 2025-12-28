package br.com.fiap.lunchtech.core.dto.usuario;

import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;

public record UsuarioDTO (String nomeUsuario,
                          String enderecoEmail,
                          String login,
                          String tipoDeUsuario,
                          EnderecoDTO endereco){
}
