package br.com.fiap.lunchtech.infra.database.dto.usuario;

import br.com.fiap.lunchtech.infra.database.entities.EnderecoEntity;
import br.com.fiap.lunchtech.infra.database.entities.TipoUsuarioEntity;

public record AlterarUsuarioDTO(Long id,
                                String nomeUsuario,
                                String enderecoEmail,
                                String login,
                                TipoUsuarioEntity tipoDeUsuario,
                                EnderecoEntity endereco) {
}
