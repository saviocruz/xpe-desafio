package com.xpe.arquitetura.repository;

import com.xpe.arquitetura.model.Pedido;
import com.xpe.arquitetura.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para operações de persistência de Pedido.
 * 
 * Estende JpaRepository para fornecer operações CRUD básicas
 * e adiciona métodos de consulta customizados.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /**
     * Busca pedidos de um cliente específico.
     * 
     * @param clienteId ID do cliente
     * @return Lista de pedidos do cliente
     */
    List<Pedido> findByClienteId(Long clienteId);

    /**
     * Busca pedidos por status.
     * 
     * @param status Status do pedido
     * @return Lista de pedidos com o status informado
     */
    List<Pedido> findByStatus(StatusPedido status);

    /**
     * Busca pedidos dentro de um período de data.
     * 
     * @param dataInicio Data de início
     * @param dataFim Data de fim
     * @return Lista de pedidos no período
     */
    List<Pedido> findByDataPedidoBetween(LocalDateTime dataInicio, LocalDateTime dataFim);

    /**
     * Conta quantos pedidos existem no sistema.
     * 
     * @return Número total de pedidos
     */
    @Query("SELECT COUNT(p) FROM Pedido p")
    long countPedidos();

    /**
     * Conta quantos pedidos existem para um cliente.
     * 
     * @param clienteId ID do cliente
     * @return Número de pedidos do cliente
     */
    long countByClienteId(Long clienteId);
}
