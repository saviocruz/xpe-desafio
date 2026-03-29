package com.xpe.arquitetura.exception;

/**
 * Exceção lançada quando não há estoque suficiente de um produto.
 */
public class EstoqueInsuficienteException extends RuntimeException {
    
    public EstoqueInsuficienteException(String produtoNome, int solicitado, int disponivel) {
        super(String.format("Estoque insuficiente para o produto '%s'. Solicitado: %d, Disponível: %d", 
                produtoNome, solicitado, disponivel));
    }
}
