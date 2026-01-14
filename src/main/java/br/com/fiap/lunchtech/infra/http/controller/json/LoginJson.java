package br.com.fiap.lunchtech.infra.http.controller.json;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginJson {
    @NotBlank
    private String login;
    @NotBlank
    private String senha;
}
