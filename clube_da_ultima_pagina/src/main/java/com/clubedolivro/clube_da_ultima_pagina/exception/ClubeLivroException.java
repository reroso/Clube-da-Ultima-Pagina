package com.clubedolivro.clube_da_ultima_pagina.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção geral da aplicação para erros não específicos
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ClubeLivroException extends RuntimeException {
    
    public ClubeLivroException(String mensagem) {
        super(mensagem);
    }
    
    public ClubeLivroException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
