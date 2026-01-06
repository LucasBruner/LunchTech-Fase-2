package br.com.fiap.lunchtech.infra.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioApiController {

    @Operation(
            summary = "Busca usuários",
            description = "Busca usuários de forma paginada. O filtro por nome é opcional. Retorna uma lista de JSON. Exemplo (todos): http://localhost:8080/v1/usuarios?page=1&size=10. Exemplo (com filtro): http://localhost:8080/v1/usuarios?page=1&size=10&name=Lucas",
            responses = { @ApiResponse(description = "Ok", responseCode = "200")})
    @GetMapping
    public String teste() {
        return "Teste brenda";
    }

}
