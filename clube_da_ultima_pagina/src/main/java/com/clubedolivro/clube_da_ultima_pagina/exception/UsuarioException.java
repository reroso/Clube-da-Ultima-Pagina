package com.clubedolivro.clube_da_ultima_pagina.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção específica para operações relacionadas a usuários
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsuarioException extends RuntimeException {
    
    public UsuarioException(String mensagem) {
        super(mensagem);
    }
    
    public UsuarioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}