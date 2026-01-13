package br.com.fiap.lunchtech.infra.http.controller;

import br.com.fiap.lunchtech.core.controllers.CardapioController;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioAlteradoDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioBuscarDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.CardapioDTO;
import br.com.fiap.lunchtech.core.dto.cardapio.NovoCardapioDTO;
import br.com.fiap.lunchtech.core.interfaces.ICardapioDataSource;
import br.com.fiap.lunchtech.core.interfaces.IRestauranteDataSource;
import br.com.fiap.lunchtech.core.interfaces.IUsuarioDataSource;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/cardapio")
@Tag(name = "Cardapio", description = "Endpoints para gerenciamento de cardápios")
public class CardapioApiController {
    private final CardapioController cardapioController;

    public CardapioApiController(ICardapioDataSource cardapioDataSource,
                                 IRestauranteDataSource restauranteDataSource,
                                 IUsuarioDataSource usuarioDataSource) {
        this.cardapioController = CardapioController.create(cardapioDataSource, restauranteDataSource, usuarioDataSource);
    }

    @Operation(
            summary = "Busca o item do cardapio de um restaurante",
            description = "Busca o item do cardapio de um restaurante. Retorna o item do cardápio pesquisado. " +
                    "Exemplo: http://localhost:8080/v1/cardapio/nome",
            responses = {@ApiResponse(description =  "Ok", responseCode = "200")})
    @GetMapping
    public ResponseEntity<CardapioDTO> buscarPorNome(@Valid @RequestBody CardapioBuscarDTO cardapioDTO) {
        CardapioDTO cardapio = cardapioController.buscarProduto(cardapioDTO);
        return ResponseEntity.ok(cardapio);
    }

    @Operation(
            summary = "Inserir novo item no cardápio",
            description = "Criação de um novo item no cardápio do restaurante, " +
                    "onde são realizado as validações das regras dos campos e realiza a inserção do novo item do cardápio." +
                    " Deve-se informar um JSON com as informações do item do cardápio.",
            responses = { @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "Conflict", responseCode = "409"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @PostMapping
    public ResponseEntity<Void> criarItemCardapio(@Valid @RequestBody NovoCardapioDTO cardapio) {
        cardapioController.cadastrarProdutoCardapio(cardapio);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Altera informações de um item do cardápio",
            description = "Alteração das informações de um item do cardápio, onde são realizado as validações das regras dos campos e realiza a alteração dos dados do item alterado." +
                    " Deve-se informar um JSON com as informações do item do cardápio.",
            responses = { @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "Conflict", responseCode = "409"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @PutMapping
    public ResponseEntity<Void> alterarRestaurante(@Valid @RequestBody CardapioAlteradoDTO alteracaoCardapio) {
        cardapioController.alterarProdutoCardapio(alteracaoCardapio);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Exclusão de um item do cardápio",
            description = "Exclusão de um item do cardápio. Deve-se informar um JSON com as informações do item do cardápio.",
            responses = { @ApiResponse(description = "Ok", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRestaurante(@PathVariable Long id) {
        cardapioController.deletarProdutoCardapio(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Buscar itens do cardápio.",
            description = "Busca todos os itens do cardápio referente a um restaurante. Exemplo: http://localhost:8080/v1/cardapio/restaurante/nome",
            responses = {@ApiResponse(description =  "Ok", responseCode = "200"),
            @ApiResponse(description = "Not found", responseCode = "404")}
    )
    @GetMapping("/restaurante/{nome}")
    public ResponseEntity<List<CardapioDTO>> buscarCardapioRestaurante(@PathVariable String nome) {
        List<CardapioDTO> cardapio = cardapioController.buscarListaDoCardapio(nome);
        return ResponseEntity.ok(cardapio);
    }
}
