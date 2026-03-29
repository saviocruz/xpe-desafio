package com.xpe.arquitetura.exception;

/**
 * Exceção lançada quando um recurso não é encontrado.
 * 
 * Esta exceção é utilizada para indicar que um recurso solicitado
 * não foi encontrado no sistema (HTTP 404 Not Found).
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
