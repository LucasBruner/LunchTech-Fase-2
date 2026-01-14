package br.com.fiap.lunchtech.infra.http.controller.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsuarioJson {
    @NotBlank
    private String login;
    @NotBlank
    private String nomeUsuario;
    @NotBlank
    private String enderecoEmail;
    private String senha;
    @NotBlank
    private String tipoDeUsuario;
    @NotNull
    private EnderecoJson endereco;
}
