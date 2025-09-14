package com.clubedolivro.clube_da_ultima_pagina.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Controlador global para tratamento de exceções da aplicação
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceções específicas de livros
     */
    @ExceptionHandler(LivroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleLivroException(LivroException ex, Model model) {
        model.addAttribute("titulo", "Erro - Livros");
        model.addAttribute("mensagem", ex.getMessage());
        model.addAttribute("detalhes", "Ocorreu um problema ao processar a operação com livros.");
        model.addAttribute("codigoErro", "400");
        model.addAttribute("voltarPara", "/livro");
        model.addAttribute("textoBotao", "Voltar para Livros");
        return "erro/erro-livro";
    }

    /**
     * Trata exceções gerais da aplicação
     */
    @ExceptionHandler(ClubeLivroException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleClubeLivroException(ClubeLivroException ex, Model model) {
        model.addAttribute("titulo", "Erro Interno");
        model.addAttribute("mensagem", ex.getMessage());
        model.addAttribute("detalhes", "Ocorreu um erro interno na aplicação. Nossa equipe foi notificada.");
        model.addAttribute("codigoErro", "500");
        model.addAttribute("voltarPara", "/");
        model.addAttribute("textoBotao", "Voltar ao Início");
        return "erro/erro-geral";
    }

    /**
     * Trata exceções gerais não capturadas
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("titulo", "Erro Inesperado");
        model.addAttribute("mensagem", "Ops! Algo deu errado.");
        model.addAttribute("detalhes", "Ocorreu um erro inesperado. Nossa equipe foi notificada e está trabalhando para resolver.");
        model.addAttribute("codigoErro", "500");
        model.addAttribute("voltarPara", "/");
        model.addAttribute("textoBotao", "Voltar ao Início");
        return "erro/erro-geral";
    }

    /**
     * Trata erro 404 - Página não encontrada
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(NoHandlerFoundException ex, Model model) {
        model.addAttribute("titulo", "Página Não Encontrada");
        model.addAttribute("mensagem", "A página que você procura não foi encontrada.");
        model.addAttribute("detalhes", "Verifique se o endereço está correto ou use o menu para navegar.");
        model.addAttribute("codigoErro", "404");
        model.addAttribute("voltarPara", "/");
        model.addAttribute("textoBotao", "Voltar ao Início");
        return "erro/erro-geral";
    }
}
