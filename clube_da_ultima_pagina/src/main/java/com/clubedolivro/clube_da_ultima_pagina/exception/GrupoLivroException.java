package com.clubedolivro.clube_da_ultima_pagina.exception;

/**
 * Exceção específica para operações relacionadas à entidade GrupoLivro.
 * Utilizada para tratar erros de negócio e validações específicas da associação entre Grupo e Livro.
 */
public class GrupoLivroException extends RuntimeException {
    
    /**
     * Constrói uma nova exceção com a mensagem especificada.
     *
     * @param message a mensagem de erro
     */
    public GrupoLivroException(String message) {
        super(message);
    }
    
    /**
     * Constrói uma nova exceção com a mensagem especificada e a causa.
     *
     * @param message a mensagem de erro
     * @param cause a causa da exceção
     */
    public GrupoLivroException(String message, Throwable cause) {
        super(message, cause);
    }
}