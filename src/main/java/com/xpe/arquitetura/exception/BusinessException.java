package com.xpe.arquitetura.exception;

/**
 * Exceção lançada para erros de negócio.
 * 
 * Esta exceção é utilizada para indicar que uma regra de negócio
 * foi violada (HTTP 400 Bad Request).
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
