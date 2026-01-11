package br.com.fiap.lunchtech.infra.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/restaurantes")
@Tag(name = "Restaurantes", description = "Endpoints para gerenciamento de restaurantes")
public class RestauranteApiController {

    @Operation(
            summary = "Busca restaurantes",
            description = "Busca restaurantes.Exemplo: http://localhost:8080/v1/restaurantes",
            responses = { @ApiResponse(description = "Ok", responseCode = "200")})
    @GetMapping
    public String teste() {
        return "Teste brenda";
    }

}
