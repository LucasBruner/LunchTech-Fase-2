package br.com.fiap.lunchtech.infra.database.dto.usuario;

import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;

public record NovoUsuarioDTO(String nomeUsuario,
                             String enderecoEmail,
                             String login,
                             String senha,
                             TipoUsuarioEntity tipoDeUsuario,
                             EnderecoEntity endereco) {
}
