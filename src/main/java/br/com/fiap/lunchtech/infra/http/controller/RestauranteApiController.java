package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.RestauranteController;
import br.com.fiap.lunchtech.core.dto.endereco.EnderecoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.NovoRestauranteDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteAlteracaoDTO;
import br.com.fiap.lunchtech.core.dto.restaurante.RestauranteDTO;
import br.com.fiap.lunchtech.core.dto.usuario.UsuarioDonoRestauranteDTO;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.ITipoUsuarioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
import br.com.fiap.lunchtech.infra.http.controller.json.RestauranteJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            summary = "Busca de restaurante",
            description = "Busca o restaurante a partir do nome. Retorna o restaurante pesquisado. Exemplo: http://localhost:8080/v1/restaurantes/nome",
            responses = {@ApiResponse(description =  "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @GetMapping("/{nome}")
    public ResponseEntity<RestauranteDTO> buscarPorNome(@PathVariable String nome) {
        RestauranteDTO restaurante = restauranteController.buscarRestaurantePorNome(nome);
        return ResponseEntity.ok(restaurante);
    }

    @Operation(
            summary = "Inserir novo restaurante",
            description = "Criação de um novo restaurante, onde são realizado as validações das regras dos campos e realiza a inserção do novo restaurante." +
                    " Deve-se informar um JSON com as informações de restaurante.",
            responses = { @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "Conflict", responseCode = "409"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @PostMapping
    public ResponseEntity<Void> criarRestaurante(@Valid @RequestBody RestauranteJson restaurante) {
        restauranteController.cadastrarRestaurante(mapToNovoRestauranteDTO(restaurante));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Altera informações de um restaurante",
            description = "Alteração das informações de um restaurante, onde são realizado as validações das regras dos campos e realiza a alteração dos dados do restaurante." +
                    " Deve-se informar um JSON com as informações de restaurante.",
            responses = { @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "Conflict", responseCode = "409"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @PutMapping
    public ResponseEntity<Void> alterarRestaurante(@Valid @RequestBody RestauranteJson restauranteJson) {
        restauranteController.alterarRestaurante(mapToRestauranteAlteracaoDTO(restauranteJson));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Exclusão de um restaurante",
            description = "Exclusão de um restaurante. Deve-se informar o nome do restaurante que será excluído. Exemplo: http://localhost:8080/v1/restaurantes/Cantina da Nona",
            responses = { @ApiResponse(description = "Ok", responseCode = "200"), @ApiResponse(description = "Not found", responseCode = "404")})
    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletarRestaurante(@PathVariable String nome) {
        restauranteController.deletarRestaurante(nome);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Busca todos os restaurantes",
            description = "Busca todos os restaurantes de acordo com a páginação e tamanho da lista. O padrão é page 0 e size 10." +
                    " Exemplo: http://localhost:8080/v1/restaurantes/lista?page=0&size=10",
            responses = { @ApiResponse(description = "Ok", responseCode = "200"), @ApiResponse(description = "Not found", responseCode = "404")}
    )
    @GetMapping("/lista")
    public ResponseEntity<List<RestauranteDTO>> buscarListaRestaurantes(@RequestParam(value = "page", required = false) Integer page,
                                                                        @RequestParam(value = "size", required = false) Integer size) {
        List<RestauranteDTO> restaurantes = restauranteController.buscarListaRestaurantes(page, size);
        return ResponseEntity.ok(restaurantes);
    }

    private NovoRestauranteDTO mapToNovoRestauranteDTO(RestauranteJson restaurante) {
        return new NovoRestauranteDTO(
                restaurante.getNomeRestaurante(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamentoInicio(),
                restaurante.getHorarioFuncionamentoFim(),
                new EnderecoDTO(
                        restaurante.getEndereco().getLogradouro(),
                        restaurante.getEndereco().getNumero(),
                        restaurante.getEndereco().getBairro(),
                        restaurante.getEndereco().getCidade(),
                        restaurante.getEndereco().getEstado(),
                        restaurante.getEndereco().getCep()
                        ),
                new UsuarioDonoRestauranteDTO(
                        restaurante.getLogin(),
                        null
                )
        );
    }

    private RestauranteAlteracaoDTO mapToRestauranteAlteracaoDTO(RestauranteJson restaurante) {
        return new RestauranteAlteracaoDTO(
                restaurante.getIdRestaurante(),
                restaurante.getNomeRestaurante(),
                restaurante.getTipoCozinha(),
                restaurante.getHorarioFuncionamentoInicio(),
                restaurante.getHorarioFuncionamentoFim(),
                new EnderecoDTO(
                        restaurante.getEndereco().getLogradouro(),
                        restaurante.getEndereco().getNumero(),
                        restaurante.getEndereco().getBairro(),
                        restaurante.getEndereco().getCidade(),
                        restaurante.getEndereco().getEstado(),
                        restaurante.getEndereco().getCep()
                ),
                new UsuarioDonoRestauranteDTO(
                        restaurante.getLogin(),
                        null
                )
        );
    }
}
