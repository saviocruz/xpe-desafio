package com.xpe.arquitetura.adapter;

import com.xpe.arquitetura.dto.ItemPedidoDTO;
import com.xpe.arquitetura.model.ItemPedido;

/**
 * Adapter para conversão entre ItemPedido e ItemPedidoDTO.
 */
public class ItemPedidoAdapter {

    /**
     * Converte uma entidade ItemPedido para ItemPedidoDTO.
     * 
     * @param itemPedido Entidade ItemPedido
     * @return ItemPedidoDTO com os dados do item
     */
    public static ItemPedidoDTO toDTO(ItemPedido itemPedido) {
        if (itemPedido == null) {
            return null;
        }
        
        ItemPedidoDTO dto = new ItemPedidoDTO();
        dto.setId(itemPedido.getId());
        dto.setQuantidade(itemPedido.getQuantidade());
        dto.setPrecoUnitario(itemPedido.getPrecoUnitario());
        dto.setSubtotal(itemPedido.getSubtotal());
        
        if (itemPedido.getProduto() != null) {
            dto.setProdutoId(itemPedido.getProduto().getId());
            dto.setProdutoNome(itemPedido.getProduto().getNome());
        }
        
        return dto;
    }
}
