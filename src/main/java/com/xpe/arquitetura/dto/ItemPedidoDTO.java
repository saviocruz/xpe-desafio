package com.xpe.arquitetura.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para representação de Item de Pedido em respostas da API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoDTO {
    
    private Long id;
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;
}
