package br.com.fiap.lunchtech.infra.http.controller.json;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CardapioJson {
    private Long id;
    @NotBlank
    private String nomeProduto;
    @NotBlank
    private String descricao;
    @NotNull
    private double preco;
    @NotNull
    private boolean apenasPresencial;
    @NotBlank
    private String fotoPrato;
    @NotNull
    private Long restauranteId;
}
