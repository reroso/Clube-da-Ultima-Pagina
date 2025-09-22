package com.clubedolivro.clube_da_ultima_pagina.exception;

/**
 * Exceção específica para operações relacionadas à entidade Encontro.
 * Utilizada para tratar erros de negócio e validações específicas de encontros.
 */
public class EncontroException extends RuntimeException {
    
    /**
     * Constrói uma nova exceção com a mensagem especificada.
     *
     * @param message a mensagem de erro
     */
    public EncontroException(String message) {
        super(message);
    }
    
    /**
     * Constrói uma nova exceção com a mensagem especificada e a causa.
     *
     * @param message a mensagem de erro
     * @param cause a causa da exceção
     */
    public EncontroException(String message, Throwable cause) {
        super(message, cause);
    }
}