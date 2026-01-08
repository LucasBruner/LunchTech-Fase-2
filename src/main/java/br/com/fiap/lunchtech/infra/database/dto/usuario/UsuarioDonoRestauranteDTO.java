package br.com.fiap.lunchtech.infra.database.dto.usuario;

public record UsuarioDonoRestauranteDTO (String nomeUsuario,
                                         String enderecoEmail,
                                         String tipoDeUsuario,
                                         String login){
}
