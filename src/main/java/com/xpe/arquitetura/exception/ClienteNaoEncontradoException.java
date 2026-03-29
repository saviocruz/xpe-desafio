package com.xpe.arquitetura.exception;

/**
 * Exceção lançada quando um Cliente não é encontrado.
 */
public class ClienteNaoEncontradoException extends RuntimeException {
    
    public ClienteNaoEncontradoException(Long id) {
        super("Cliente não encontrado com ID: " + id);
    }
    
    public ClienteNaoEncontradoException(String email) {
        super("Cliente não encontrado com email: " + email);
    }
}
