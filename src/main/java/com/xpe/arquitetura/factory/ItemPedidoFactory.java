package com.xpe.arquitetura.factory;

import java.math.BigDecimal;

import com.xpe.arquitetura.dto.ItemPedidoRequestDTO;
import com.xpe.arquitetura.model.ItemPedido;
import com.xpe.arquitetura.model.Pedido;
import com.xpe.arquitetura.model.Produto;

/**
 * Factory para criação de instâncias de ItemPedido.
 */
public class ItemPedidoFactory {

    /**
     * Cria uma nova instância de ItemPedido a partir de um DTO de requisição.
     * 
     * @param requestDTO DTO com os dados do item
     * @param pedido Pedido associado ao item
     * @param produto Produto associado ao item
     * @return Nova instância de ItemPedido
     */
    public static ItemPedido criarItemPedido(ItemPedidoRequestDTO requestDTO, 
            Pedido pedido, Produto produto) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(requestDTO.getQuantidade());
        itemPedido.setPrecoUnitario(produto.getPreco());
        itemPedido.setSubtotal(produto.getPreco().multiply(BigDecimal.valueOf(requestDTO.getQuantidade())));
        return itemPedido;
    }
}
