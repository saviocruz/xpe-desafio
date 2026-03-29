package com.xpe.arquitetura.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um Pedido no sistema.
 * 
 * Esta entidade é mapeada para a tabela "pedidos" no banco de dados H2.
 * Um Pedido pertence a um Cliente e contém múltiplos Itens de Pedido.
 */
@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusPedido status;

    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    /**
     * Método executado antes de persistir a entidade.
     */
    @PrePersist
    protected void onCreate() {
        dataPedido = LocalDateTime.now();
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
        if (status == null) {
            status = StatusPedido.AGUARDANDO_PAGAMENTO;
        }
        if (valorTotal == null) {
            valorTotal = BigDecimal.ZERO;
        }
    }

    /**
     * Método executado antes de atualizar a entidade.
     */
    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    /**
     * Adiciona um item ao pedido e recalcula o valor total.
     */
    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        item.setPedido(this);
        recalcularValorTotal();
    }

    /**
     * Recalcula o valor total do pedido baseado nos itens.
     */
    public void recalcularValorTotal() {
        this.valorTotal = itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Cancela o pedido.
     */
    public boolean cancelar() {
        if (this.status == StatusPedido.ENTREGUE) {
            return false;
        }
        this.status = StatusPedido.CANCELADO;
        return true;
    }
}
