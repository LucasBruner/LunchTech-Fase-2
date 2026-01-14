package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.UsuarioController;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioSenhaDTO;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.infra.http.controller.json.LoginJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/login")
@Tag(name = "Login", description = "Endpoints para gerenciamento de login")
public class LoginUsuarioApiController {

    private final UsuarioController usuarioController;

    public LoginUsuarioApiController(IUsuarioDataSource usuarioDataSource) {
        this.usuarioController = UsuarioController.create(usuarioDataSource);
    }

    @Operation(
            summary = "Login de usuário",
            description = "Faz a validação de usuário e senha para login. Deve-se informar o login de usuario e senha. Exemplo: http://localhost:8080/login",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @PostMapping
    public ResponseEntity<Void> validarLogin(@Valid @RequestBody LoginJson loginJson) {
        usuarioController.validarLoginUsuario(mapToUsuarioSenhaDTO(loginJson));
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Alteração da senha do usuário",
            description = "Alteração da senha do usuário. Deve-se informar um JSON com as informações do usuário e a nova senha. A senha não pode ser vazia ou possuir apenas espaços. A data de alteração será atualizada automaticamente com a data atual do sistema. Exemplo: http://localhost:8080/login",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @PutMapping("/senha")
    public ResponseEntity<Void> alterarSenha(@Valid @RequestBody LoginJson loginJson) {
        usuarioController.alterarSenhaUsuario(mapToUsuarioSenhaDTO(loginJson));
        return ResponseEntity.ok().build();
    }

    private UsuarioSenhaDTO mapToUsuarioSenhaDTO(LoginJson loginJson) {
        return new UsuarioSenhaDTO(
                loginJson.getLogin(),
                loginJson.getSenha(),
                LocalDateTime.now()
        );
    }
}
