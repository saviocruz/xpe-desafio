package com.xpe.arquitetura.controller;

import com.xpe.arquitetura.dto.PedidoDTO;
import com.xpe.arquitetura.dto.PedidoRequestDTO;
import com.xpe.arquitetura.model.StatusPedido;
import com.xpe.arquitetura.service.PedidoService;
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
 * Controller REST para operações com Pedidos.
 */
@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    /**
     * Cria um novo pedido.
     */
    @PostMapping
    @Operation(summary = "Criar pedido", description = "Cria um novo pedido no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou estoque insuficiente"),
        @ApiResponse(responseCode = "404", description = "Cliente ou produto não encontrado")
    })
    public ResponseEntity<PedidoDTO> criar(@Valid @RequestBody PedidoRequestDTO request) {
        PedidoDTO pedido = pedidoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    /**
     * Lista todos os pedidos.
     */
    @GetMapping
    @Operation(summary = "Listar pedidos", description = "Lista todos os pedidos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    })
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        List<PedidoDTO> pedidos = pedidoService.buscarTodos();
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Busca um pedido pelo ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Busca um pedido específico pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<PedidoDTO> buscarPorId(@PathVariable Long id) {
        PedidoDTO pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }

    /**
     * Lista pedidos de um cliente.
     */
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar pedidos por cliente", description = "Lista todos os pedidos de um cliente específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    })
    public ResponseEntity<List<PedidoDTO>> buscarPorCliente(@PathVariable Long clienteId) {
        List<PedidoDTO> pedidos = pedidoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Lista pedidos por status.
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Listar pedidos por status", description = "Lista todos os pedidos com um status específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    })
    public ResponseEntity<List<PedidoDTO>> buscarPorStatus(@PathVariable StatusPedido status) {
        List<PedidoDTO> pedidos = pedidoService.buscarPorStatus(status);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * Conta o total de pedidos.
     */
    @GetMapping("/count")
    @Operation(summary = "Contar pedidos", description = "Retorna o número total de pedidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso")
    })
    public ResponseEntity<Map<String, Long>> contar() {
        long total = pedidoService.contar();
        return ResponseEntity.ok(Map.of("total", total));
    }

    /**
     * Atualiza o status de um pedido.
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Não é possível atualizar o status"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<PedidoDTO> atualizarStatus(@PathVariable Long id, 
                                                      @RequestBody Map<String, String> body) {
        StatusPedido novoStatus = StatusPedido.valueOf(body.get("status"));
        PedidoDTO pedido = pedidoService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(pedido);
    }

    /**
     * Remove (cancela) um pedido.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar pedido", description = "Cancela um pedido do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Não é possível cancelar o pedido"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        pedidoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
