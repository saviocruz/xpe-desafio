package com.xpe.arquitetura.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.xpe.arquitetura.dto.PedidoDTO;
import com.xpe.arquitetura.model.Pedido;

/**
 * Adapter para conversão entre Pedido e seus DTOs.
 * 
 * Esta classe implementa o padrão Adapter para centralizar
 * a lógica de conversão entre entidades e DTOs de Pedido.
 */
public class PedidoAdapter {

    /**
     * Converte uma entidade Pedido para PedidoDTO.
     * 
     * @param pedido Entidade Pedido
     * @return PedidoDTO com os dados do pedido
     */
    public static PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setStatus(pedido.getStatus());
        dto.setValorTotal(pedido.getValorTotal());
        
        if (pedido.getCliente() != null) {
            dto.setClienteId(pedido.getCliente().getId());
            dto.setClienteNome(pedido.getCliente().getNome());
        }
        
        if (pedido.getItens() != null) {
            dto.setItens(pedido.getItens().stream()
                    .map(ItemPedidoAdapter::toDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setItens(new ArrayList<>());
        }
        
        return dto;
    }

    /**
     * Converte uma lista de Pedido para uma lista de PedidoDTO.
     * 
     * @param pedidos Lista de entidades Pedido
     * @return Lista de PedidoDTO
     */
    public static List<PedidoDTO> toDTOList(List<Pedido> pedidos) {
        if (pedidos == null) {
            return new ArrayList<>();
        }
        return pedidos.stream()
                .map(PedidoAdapter::toDTO)
                .collect(Collectors.toList());
    }
}
