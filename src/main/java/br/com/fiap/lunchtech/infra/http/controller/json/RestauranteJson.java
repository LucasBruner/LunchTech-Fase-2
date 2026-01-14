package br.com.fiap.lunchtech.infra.http.controller.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class RestauranteJson {
    private Long idRestaurante;
    @NotBlank
    private String nomeRestaurante;
    @NotBlank
    private String tipoCozinha;
    @NotNull
    private LocalTime horarioFuncionamentoInicio;
    @NotNull
    private LocalTime horarioFuncionamentoFim;
    @NotNull
    private EnderecoJson endereco;
    @NotBlank
    private String login;
}
