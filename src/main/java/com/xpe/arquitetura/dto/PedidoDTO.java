package com.xpe.arquitetura.dto;

import com.xpe.arquitetura.model.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para representação de Pedido em respostas da API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    
    private Long id;
    private LocalDateTime dataPedido;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private Long clienteId;
    private String clienteNome;
    private List<ItemPedidoDTO> itens;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
