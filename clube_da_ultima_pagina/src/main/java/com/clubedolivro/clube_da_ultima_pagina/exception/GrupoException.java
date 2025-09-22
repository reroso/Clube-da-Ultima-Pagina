package com.clubedolivro.clube_da_ultima_pagina.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção específica para operações relacionadas a grupos
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GrupoException extends RuntimeException {
    
    public GrupoException(String mensagem) {
        super(mensagem);
    }
    
    public GrupoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}