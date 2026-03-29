package com.xpe.arquitetura.controller;

import com.xpe.arquitetura.dto.ProdutoDTO;
import com.xpe.arquitetura.dto.ProdutoRequestDTO;
import com.xpe.arquitetura.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações de Produto.
 * 
 * Este controller expõe endpoints para CRUD de produtos,
 * seguindo os padrões REST e utilizando autenticação JWT.
 */
@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
@SecurityRequirement(name = "Bearer Authentication")
public class ProdutoController {

    private final ProdutoService produtoService;

    /**
     * Cria um novo produto.
     * 
     * @param dto DTO com os dados do produto
     * @return ResponseEntity com o produto criado (201)
     */
    @PostMapping
    @Operation(summary = "Criar produto", description = "Cria um novo produto no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<ProdutoDTO> criarProduto(@Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoDTO produtoCriado = produtoService.criarProduto(dto);
        return new ResponseEntity<>(produtoCriado, HttpStatus.CREATED);
    }

    /**
     * Lista todos os produtos cadastrados.
     * 
     * @return ResponseEntity com a lista de produtos (200)
     */
    @GetMapping
    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<ProdutoDTO>> listarTodos() {
        List<ProdutoDTO> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    /**
     * Busca um produto pelo ID.
     * 
     * @param id ID do produto
     * @return ResponseEntity com o produto encontrado (200)
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto encontrado"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<ProdutoDTO> buscarPorId(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        ProdutoDTO produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    /**
     * Busca produtos pelo nome (parcial, case insensitive).
     * 
     * @param nome Nome ou parte do nome do produto
     * @return ResponseEntity com a lista de produtos encontrados (200)
     */
    @GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar produtos por nome", description = "Retorna produtos que contenham o nome informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produtos encontrados"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<ProdutoDTO>> buscarPorNome(
            @Parameter(description = "Nome ou parte do nome do produto") @PathVariable String nome) {
        List<ProdutoDTO> produtos = produtoService.buscarPorNome(nome);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Atualiza um produto existente.
     * 
     * @param id ID do produto a ser atualizado
     * @param dto DTO com os novos dados do produto
     * @return ResponseEntity com o produto atualizado (200)
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<ProdutoDTO> atualizarProduto(
            @Parameter(description = "ID do produto") @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoDTO produtoAtualizado = produtoService.atualizarProduto(id, dto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    /**
     * Deleta um produto pelo ID.
     * 
     * @param id ID do produto a ser deletado
     * @return ResponseEntity sem conteúdo (204)
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto", description = "Deleta um produto existente pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<Void> deletarProduto(
            @Parameter(description = "ID do produto") @PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Conta o total de produtos cadastrados.
     * 
     * @return ResponseEntity com o número total de produtos (200)
     */
    @GetMapping("/count")
    @Operation(summary = "Contar produtos", description = "Retorna o número total de produtos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<Long> contarProdutos() {
        long total = produtoService.contarProdutos();
        return ResponseEntity.ok(total);
    }
}
