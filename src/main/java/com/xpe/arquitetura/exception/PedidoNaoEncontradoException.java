package com.xpe.arquitetura.exception;

/**
 * Exceção lançada quando um Pedido não é encontrado.
 */
public class PedidoNaoEncontradoException extends RuntimeException {
    
    public PedidoNaoEncontradoException(Long id) {
        super("Pedido não encontrado com ID: " + id);
    }
}
