package com.xpe.arquitetura.factory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.xpe.arquitetura.dto.ItemPedidoRequestDTO;
import com.xpe.arquitetura.dto.PedidoRequestDTO;
import com.xpe.arquitetura.model.Cliente;
import com.xpe.arquitetura.model.ItemPedido;
import com.xpe.arquitetura.model.Pedido;
import com.xpe.arquitetura.model.Produto;
import com.xpe.arquitetura.model.StatusPedido;

/**
 * Factory para criação de instâncias de Pedido.
 * 
 * Esta classe implementa o padrão Factory para centralizar
 * a lógica de criação de entidades Pedido.
 */
public class PedidoFactory {

    /**
     * Cria uma nova instância de Pedido a partir de um DTO de requisição.
     * 
     * @param requestDTO DTO com os dados do pedido
     * @param cliente Cliente associado ao pedido
     * @param produtos Map de produtos para associar aos itens
     * @return Nova instância de Pedido
     */
    public static Pedido criarPedido(PedidoRequestDTO requestDTO, Cliente cliente, 
            java.util.Map<Long, Produto> produtos) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setItens(new ArrayList<>());
        
        BigDecimal valorTotal = BigDecimal.ZERO;
        
        for (ItemPedidoRequestDTO itemDTO : requestDTO.getItens()) {
            Produto produto = produtos.get(itemDTO.getProdutoId());
            if (produto != null) {
                ItemPedido itemPedido = ItemPedidoFactory.criarItemPedido(itemDTO, pedido, produto);
                pedido.getItens().add(itemPedido);
                valorTotal = valorTotal.add(itemPedido.getSubtotal());
            }
        }
        
        pedido.setValorTotal(valorTotal);
        return pedido;
    }

    /**
     * Atualiza o status de um pedido.
     * 
     * @param pedido Pedido a ser atualizado
     * @param novoStatus Novo status do pedido
     * @return Pedido atualizado
     */
    public static Pedido atualizarStatus(Pedido pedido, StatusPedido novoStatus) {
        pedido.setStatus(novoStatus);
        return pedido;
    }

    /**
     * Recalcula o valor total de um pedido.
     * 
     * @param pedido Pedido a ter o valor recalculado
     * @return Pedido com valor total atualizado
     */
    public static Pedido recalcularValorTotal(Pedido pedido) {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ItemPedido item : pedido.getItens()) {
            valorTotal = valorTotal.add(item.getSubtotal());
        }
        pedido.setValorTotal(valorTotal);
        return pedido;
    }
}
