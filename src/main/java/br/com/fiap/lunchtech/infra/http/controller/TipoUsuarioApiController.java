package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.TipoUsuarioController;
import br.com.fiap.lunchtech.core.controllers.UsuarioController;
import br.com.fiap.lunchtech.core.dto.tipoUsuario.TipoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.NovoUsuarioDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDTO;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tipo-usuario")
@Tag(name = "Tipos de usuários", description = "Endpoints para gerenciamento de tipos de usuário")
public class TipoUsuarioApiController {

    private final TipoUsuarioController tipoUsuarioController;

    public TipoUsuarioApiController(ITipoUsuarioDataSource tipoUsuarioController) {
        this.tipoUsuarioController = TipoUsuarioController.create(tipoUsuarioController);
    }

    @Operation(
            summary = "Busca de tipos de usuários",
            description = "Busca tipos de usuários a partir do nome ou todos. Exemplo: http://localhost:8080/v1/tipo-usuario",
            responses = {@ApiResponse(description =  "Ok", responseCode = "200")})
    @GetMapping()
    public ResponseEntity<List<TipoUsuarioDTO>> buscarPorNome(@RequestParam(value = "tipo", required = false) String tipo) {
        List<TipoUsuarioDTO> tipoUsuario = tipoUsuarioController.buscarTipoUsuario(new TipoUsuarioDTO(tipo));
        return ResponseEntity.ok(tipoUsuario);
    }

    @Operation(
            summary = "Criação de novo tipo de usuário",
            description = "Criação de novo tipo de usuário, onde são feitas as validações das regras de negócio e salva o novo tipo de usuário. Deve-se informar um JSON com as informações de tipo de usuário.",
            responses = { @ApiResponse(description = "Created", responseCode = "201"), @ApiResponse(description = "Bad request", responseCode = "400")})
    @PostMapping
    public ResponseEntity<Void> criarTipoUsuario(@Valid @RequestBody TipoUsuarioDTO tipo) {
        tipoUsuarioController.cadastrarTipoUsuario(tipo);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Exclusão de tipo de usuário",
            description = "Exclusão de tipo de usuário. Deve-se informar o tipo de usuário que será excluído. Exemplo: http://localhost:8080/tipo-usuario/TIPO",
            responses = { @ApiResponse(description = "Ok", responseCode = "200"), @ApiResponse(description = "Not found", responseCode = "404")})
    @DeleteMapping("/{tipo}")
    public ResponseEntity<Void> deletarTipoUsuario(@PathVariable("tipo") String tipo) {
        tipoUsuarioController.deletarTipoUsuario(new TipoUsuarioDTO(tipo));
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Alteração de informações do usuário",
            description = "Alteração de informações do usuário, onde são feitas as validações das regras dos campos e atualiza as informações do usuário. Deve-se informar um JSON com as informações de usuário e na URL informar o email do usuário que será alterado. A data de alteração será atualizada automaticamente com a data atual do sistema.",
            responses = { @ApiResponse(description = "Ok", responseCode = "200"), @ApiResponse(description = "Bad request", responseCode = "400")})
    @PutMapping("/{email}")
    public ResponseEntity<Void> updateTipoUsuario(@Valid @RequestBody TipoUsuarioDTO tipo,
                                              @PathVariable("tipo-usuario") String tipoUsuario) {
        tipoUsuarioController.alterarTipoUsuario(tipo);
        return ResponseEntity.ok().build();
    }
}
