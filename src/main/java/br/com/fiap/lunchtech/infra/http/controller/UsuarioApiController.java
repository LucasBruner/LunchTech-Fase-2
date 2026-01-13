package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.UsuarioController;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.infra.database.datasource.RestauranteDataSource;
import br.com.fiap.lunchtech.infra.database.datasource.TipoUsuarioDataSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioApiController {

    private final UsuarioController usuarioController;
    private TipoUsuarioDataSource tipoUsuarioDataSource;
    private RestauranteDataSource restauranteDataSource;

    public UsuarioApiController(IUsuarioDataSource usuarioDataSource,
                                ITipoUsuarioDataSource tipoUsuarioDataSource,
                                IRestauranteDataSource restauranteDataSource) {
        this.usuarioController = UsuarioController.create(usuarioDataSource, restauranteDataSource, tipoUsuarioDataSource);
    }

    @Operation(
            summary = "Busca de usuários",
            description = "Busca usuários a partir do nome ou todos. Exemplo: http://localhost:8080/v1/usuarios?nome=NOME",
            responses = {
                    @ApiResponse(description =  "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @GetMapping()
    public ResponseEntity<List<UsuarioDTO>> buscarPorNome(@RequestParam(value = "nome", required = false) String nome) {
        List<UsuarioDTO> usuario = usuarioController.buscarPorNome(nome);
        return ResponseEntity.ok(usuario);
    }

    @Operation(
            summary = "Criação de novo usuário",
            description = "Criação de novo usuário, onde são feitas as validações das regras de negócio e salva o novo usuário. Deve-se informar um JSON com as informações do usuário.",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Conflict", responseCode = "409"),
                    @ApiResponse(description = "Bad request", responseCode = "400")})
    @PostMapping
    public ResponseEntity<Void> criarUsuario(@Valid @RequestBody NovoUsuarioDTO usuario) {
        usuarioController.cadastrar(usuario);
        return ResponseEntity.ok().build();
    }

}
