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

@RestController
@RequestMapping("/v2/login")
@Tag(name = "Login", description = "Endpoints para gerenciamento de login")
public class LoginUsuarioApiControllerV2 {

    private final UsuarioController usuarioController;

    public LoginUsuarioApiControllerV2(IUsuarioDataSource usuarioDataSource) {
        this.usuarioController = UsuarioController.create(usuarioDataSource);
    }

    @Operation(
            summary = "Login de usuário - v2",
            description = "Método criado para demonstração do versionamento da API. Faz a validação de usuário e senha para login. Deve-se informar o login de usuario e senha. Exemplo: http://localhost:8080/login",
            responses = {
                    @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @PostMapping
    public ResponseEntity<Void> validarLogin(@Valid @RequestBody LoginJson loginJson) {
        usuarioController.validarLoginUsuario(mapToUsuarioSenhaDTO(loginJson));
        return ResponseEntity.ok().build();
    }

    private UsuarioSenhaDTO mapToUsuarioSenhaDTO(LoginJson loginJson) {
        return new UsuarioSenhaDTO(
                loginJson.getLogin(),
                loginJson.getSenha(),
                null
        );
    }
}
