package com.clubedolivro.clube_da_ultima_pagina.exception;

import jakarta.servlet.http.HttpServletRequest;
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
    public String handleLivroException(LivroException ex, Model model, HttpServletRequest request) {
        model.addAttribute("titulo", "Erro - Livros");
        model.addAttribute("mensagem", ex.getMessage());
        model.addAttribute("detalhes", "Ocorreu um problema ao processar a operação com livros.");
        model.addAttribute("codigoErro", "400");
        
        // Definir botão de retorno baseado na URL
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/editar-livro/")) {
            model.addAttribute("voltarPara", "/lista-livros");
            model.addAttribute("textoBotao", "Voltar para Lista de Livros");
        } else {
            model.addAttribute("voltarPara", "/livro");
            model.addAttribute("textoBotao", "Voltar para Livros");
        }
        
        return "erro/erro-livro";
    }

    /**
     * Trata exceções específicas de usuários
     */
    @ExceptionHandler(UsuarioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUsuarioException(UsuarioException ex, Model model, HttpServletRequest request) {
        model.addAttribute("titulo", "Erro - Usuários");
        model.addAttribute("mensagem", ex.getMessage());
        model.addAttribute("detalhes", "Ocorreu um problema ao processar a operação com usuários.");
        model.addAttribute("codigoErro", "400");
        
        // Definir botão de retorno baseado na URL
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/editar-usuario/")) {
            model.addAttribute("voltarPara", "/lista-usuarios");
            model.addAttribute("textoBotao", "Voltar para Lista de Usuários");
        } else {
            model.addAttribute("voltarPara", "/usuario");
            model.addAttribute("textoBotao", "Voltar para Usuários");
        }
        
        return "erro/erro-geral";
    }

    /**
     * Trata exceções específicas de grupos
     */
    @ExceptionHandler(GrupoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleGrupoException(GrupoException ex, Model model, HttpServletRequest request) {
        model.addAttribute("titulo", "Erro - Grupos");
        model.addAttribute("mensagem", ex.getMessage());
        model.addAttribute("detalhes", "Ocorreu um problema ao processar a operação com grupos.");
        model.addAttribute("codigoErro", "400");
        
        // Definir botão de retorno baseado na URL
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/editar-grupo/")) {
            model.addAttribute("voltarPara", "/lista-grupos");
            model.addAttribute("textoBotao", "Voltar para Lista de Grupos");
        } else {
            model.addAttribute("voltarPara", "/grupo");
            model.addAttribute("textoBotao", "Voltar para Grupos");
        }
        
        return "erro/erro-geral";
    }

    /**
     * Trata exceções específicas de associações grupo-livro
     */
    @ExceptionHandler(GrupoLivroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleGrupoLivroException(GrupoLivroException ex, Model model, HttpServletRequest request) {
        model.addAttribute("titulo", "Erro - Associações Grupo-Livro");
        model.addAttribute("mensagem", ex.getMessage());
        model.addAttribute("detalhes", "Ocorreu um problema ao processar a operação com associações entre grupos e livros.");
        model.addAttribute("codigoErro", "400");
        
        // Definir botão de retorno baseado na URL
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/editar-grupo-livro/")) {
            model.addAttribute("voltarPara", "/lista-grupo-livro");
            model.addAttribute("textoBotao", "Voltar para Lista de Associações");
        } else {
            model.addAttribute("voltarPara", "/grupo-livro");
            model.addAttribute("textoBotao", "Voltar para Associações");
        }
        
        return "erro/erro-geral";
    }

    /**
     * Trata exceções específicas de encontros
     */
    @ExceptionHandler(EncontroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEncontroException(EncontroException ex, Model model, HttpServletRequest request) {
        model.addAttribute("titulo", "Erro - Encontros");
        model.addAttribute("mensagem", ex.getMessage());
        model.addAttribute("detalhes", "Ocorreu um problema ao processar a operação com encontros.");
        model.addAttribute("codigoErro", "400");
        
        // Definir botão de retorno baseado na URL
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/encontro/excluir/")) {
            model.addAttribute("voltarPara", "/lista-encontros");
            model.addAttribute("textoBotao", "Voltar para Lista de Encontros");
        } else if (requestURI.contains("/encontro")) {
            model.addAttribute("voltarPara", "/lista-encontros");
            model.addAttribute("textoBotao", "Voltar para Lista de Encontros");
        } else {
            model.addAttribute("voltarPara", "/encontro");
            model.addAttribute("textoBotao", "Voltar para Encontros");
        }
        
        return "erro/erro-geral";
    }

    /**
     * Trata exceções específicas de associações usuário-grupo
     */
    @ExceptionHandler(UsuarioGrupoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUsuarioGrupoException(UsuarioGrupoException ex, Model model, HttpServletRequest request) {
        model.addAttribute("titulo", "Erro - Entrada em Grupos");
        model.addAttribute("mensagem", ex.getMessage());
        model.addAttribute("detalhes", "Ocorreu um problema ao processar a operação com associações usuário-grupo.");
        model.addAttribute("codigoErro", "400");
        
        // Definir botão de retorno baseado na URL
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/usuario-grupo/sair/")) {
            model.addAttribute("voltarPara", "/lista-usuario-grupo");
            model.addAttribute("textoBotao", "Voltar para Lista de Associações");
        } else if (requestURI.contains("/lista-usuario-grupo")) {
            model.addAttribute("voltarPara", "/entrada-grupo");
            model.addAttribute("textoBotao", "Voltar para Entrada em Grupos");
        } else {
            model.addAttribute("voltarPara", "/entrada-grupo");
            model.addAttribute("textoBotao", "Voltar para Entrada em Grupos");
        }
        
        return "erro/erro-geral";
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
