package com.clubedolivro.clube_da_ultima_pagina.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção específica para operações relacionadas a livros
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LivroException extends RuntimeException {
    
    public LivroException(String mensagem) {
        super(mensagem);
    }
    
    public LivroException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
