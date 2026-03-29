package com.xpe.arquitetura.controller;

import com.xpe.arquitetura.dto.ClienteDTO;
import com.xpe.arquitetura.dto.ClienteRequestDTO;
import com.xpe.arquitetura.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para operações com Clientes.
 */
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Cria um novo cliente.
     */
    @PostMapping
    @Operation(summary = "Criar cliente", description = "Cria um novo cliente no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    public ResponseEntity<ClienteDTO> criar(@Valid @RequestBody ClienteRequestDTO request) {
        ClienteDTO cliente = clienteService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    /**
     * Lista todos os clientes.
     */
    @GetMapping
    @Operation(summary = "Listar clientes", description = "Lista todos os clientes cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    })
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        List<ClienteDTO> clientes = clienteService.buscarTodos();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Busca um cliente pelo ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Busca um cliente específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Busca clientes pelo nome.
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar clientes por nome", description = "Busca clientes cujo nome contenha o texto informado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    })
    public ResponseEntity<List<ClienteDTO>> buscarPorNome(@RequestParam String nome) {
        List<ClienteDTO> clientes = clienteService.buscarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }

    /**
     * Busca um cliente pelo email.
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar cliente por email", description = "Busca um cliente específico pelo email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<ClienteDTO> buscarPorEmail(@PathVariable String email) {
        ClienteDTO cliente = clienteService.buscarPorEmail(email);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Conta o total de clientes.
     */
    @GetMapping("/count")
    @Operation(summary = "Contar clientes", description = "Retorna o número total de clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso")
    })
    public ResponseEntity<Map<String, Long>> contar() {
        long total = clienteService.contar();
        return ResponseEntity.ok(Map.of("total", total));
    }

    /**
     * Atualiza um cliente.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, 
                                                @Valid @RequestBody ClienteRequestDTO request) {
        ClienteDTO cliente = clienteService.atualizar(id, request);
        return ResponseEntity.ok(cliente);
    }

    /**
     * Remove um cliente.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente", description = "Remove um cliente do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        clienteService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
