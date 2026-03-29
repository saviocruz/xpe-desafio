package com.xpe.arquitetura.service;

import com.xpe.arquitetura.dto.*;
import com.xpe.arquitetura.exception.*;
import com.xpe.arquitetura.model.*;
import com.xpe.arquitetura.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para operações de negócio relacionadas a Pedidos.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    /**
     * Cria um novo pedido.
     * 
     * @param request DTO com dados do pedido
     * @return DTO do pedido criado
     */
    public PedidoDTO criar(PedidoRequestDTO request) {
        // Buscar cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException(request.getClienteId()));

        // Criar pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setValorTotal(BigDecimal.ZERO);

        // Processar itens
        for (ItemPedidoRequestDTO itemRequest : request.getItens()) {
            // Buscar produto
            Produto produto = produtoRepository.findById(itemRequest.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + itemRequest.getProdutoId()));

            // Verificar estoque
            if (produto.getQuantidade() < itemRequest.getQuantidade()) {
                throw new EstoqueInsuficienteException(
                        produto.getNome(), 
                        itemRequest.getQuantidade(), 
                        produto.getQuantidade());
            }

            // Criar item de pedido
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemRequest.getQuantidade());
            itemPedido.setPrecoUnitario(produto.getPreco());
            itemPedido.setSubtotal(produto.getPreco().multiply(BigDecimal.valueOf(itemRequest.getQuantidade())));

            pedido.adicionarItem(itemPedido);

            // Decrementar estoque
            produto.setQuantidade(produto.getQuantidade() - itemRequest.getQuantidade());
            produtoRepository.save(produto);
        }

        Pedido salvo = pedidoRepository.save(pedido);
        return toDTO(salvo);
    }

    /**
     * Busca todos os pedidos.
     * 
     * @return Lista de DTOs de pedidos
     */
    @Transactional(readOnly = true)
    public List<PedidoDTO> buscarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um pedido pelo ID.
     * 
     * @param id ID do pedido
     * @return DTO do pedido
     */
    @Transactional(readOnly = true)
    public PedidoDTO buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
        return toDTO(pedido);
    }

    /**
     * Busca pedidos de um cliente.
     * 
     * @param clienteId ID do cliente
     * @return Lista de DTOs de pedidos
     */
    @Transactional(readOnly = true)
    public List<PedidoDTO> buscarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca pedidos por status.
     * 
     * @param status Status do pedido
     * @return Lista de DTOs de pedidos
     */
    @Transactional(readOnly = true)
    public List<PedidoDTO> buscarPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza o status de um pedido.
     * 
     * @param id ID do pedido
     * @param novoStatus Novo status
     * @return DTO do pedido atualizado
     */
    public PedidoDTO atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        // Se estiver cancelando um pedido entregue, não permite
        if (novoStatus == StatusPedido.CANCELADO && pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new BusinessException("Não é possível cancelar um pedido já entregue");
        }

        // Se estiver cancelando, restaurar estoque
        if (novoStatus == StatusPedido.CANCELADO && pedido.getStatus() != StatusPedido.CANCELADO) {
            restaurarEstoque(pedido);
        }

        pedido.setStatus(novoStatus);
        Pedido atualizado = pedidoRepository.save(pedido);
        return toDTO(atualizado);
    }

    /**
     * Remove (cancela) um pedido.
     * 
     * @param id ID do pedido
     */
    public void remover(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        if (!pedido.cancelar()) {
            throw new BusinessException("Não é possível cancelar um pedido já entregue");
        }

        restaurarEstoque(pedido);
        pedidoRepository.save(pedido);
    }

    /**
     * Conta o total de pedidos.
     * 
     * @return Número total de pedidos
     */
    @Transactional(readOnly = true)
    public long contar() {
        return pedidoRepository.countPedidos();
    }

    /**
     * Restaura o estoque dos produtos de um pedido.
     */
    private void restaurarEstoque(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = item.getProduto();
            produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
            produtoRepository.save(produto);
        }
    }

    /**
     * Converte entidade Pedido para DTO.
     */
    private PedidoDTO toDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setStatus(pedido.getStatus());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setClienteId(pedido.getCliente().getId());
        dto.setClienteNome(pedido.getCliente().getNome());
        dto.setDataCriacao(pedido.getDataCriacao());
        dto.setDataAtualizacao(pedido.getDataAtualizacao());

        List<ItemPedidoDTO> itensDTO = pedido.getItens().stream()
                .map(this::toItemDTO)
                .collect(Collectors.toList());
        dto.setItens(itensDTO);

        return dto;
    }

    /**
     * Converte entidade ItemPedido para DTO.
     */
    private ItemPedidoDTO toItemDTO(ItemPedido item) {
        ItemPedidoDTO dto = new ItemPedidoDTO();
        dto.setId(item.getId());
        dto.setProdutoId(item.getProduto().getId());
        dto.setProdutoNome(item.getProduto().getNome());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }
}
