package com.clubedolivro.clube_da_ultima_pagina.exception;

/**
 * Exceção específica para operações relacionadas a associações Usuario-Grupo
 */
public class UsuarioGrupoException extends RuntimeException {

    public UsuarioGrupoException(String message) {
        super(message);
    }

    public UsuarioGrupoException(String message, Throwable cause) {
        super(message, cause);
    }
}