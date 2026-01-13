package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.RestauranteController;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/restaurantes")
@Tag(name = "Restaurantes", description = "Endpoints para gerenciamento de restaurantes")
public class RestauranteApiController {

    private final RestauranteController restauranteController;

    public RestauranteApiController(IRestauranteDataSource restauranteDataSource,
                                    IUsuarioDataSource usuarioDataSource) {
        this.restauranteController = RestauranteController.create(restauranteDataSource, usuarioDataSource);
    }

    @Operation(
            summary = "Busca restaurantes",
            description = "Busca restaurantes.Exemplo: http://localhost:8080/v1/restaurantes",
            responses = { @ApiResponse(description = "Ok", responseCode = "200")})
    @GetMapping
    public String teste() {
        return "Teste brenda";
    }

    @Operation(
            summary = "Busca de restaurante",
            description = "Busca o restaurante a partir do nome. Retorna o restaurante pesquisado. Exemplo: http://localhost:8080/v1/restaurantes/nome",
            responses = {@ApiResponse(description =  "Ok", responseCode = "200")})
    @GetMapping("/{nome}")
    public ResponseEntity<RestauranteDTO> buscarPorNome(@PathVariable String nome) {
        RestauranteDTO restaurante = restauranteController.buscarRestaurantePorNome(nome);
        return ResponseEntity.ok(restaurante);
    }

    @PostMapping
    public ResponseEntity<Void> criarRestaurante(@Valid @RequestBody NovoRestauranteDTO restaurante) {
        restauranteController.cadastrarRestaurante(restaurante);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> alterarRestaurante(@Valid @RequestBody RestauranteAlteracaoDTO restaurante) {
        restauranteController.alterarRestaurante(restaurante);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletarRestaurante(@PathVariable String nome) {
        restauranteController.deletarRestaurante(nome);
        return ResponseEntity.ok().build();
    }
}
