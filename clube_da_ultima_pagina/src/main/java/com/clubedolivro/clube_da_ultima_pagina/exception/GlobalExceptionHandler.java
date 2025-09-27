package com.clubedolivro.clube_da_ultima_pagina.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.URI;
import java.time.Instant;

/**
 * Controlador global para tratamento de exceções da aplicação
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Verifica se a requisição é para uma API REST
     */
    private boolean isRestRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI != null && requestURI.startsWith("/api/");
    }

    /**
     * Trata exceções específicas de usuários
     */
    @ExceptionHandler(UsuarioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleUsuarioException(UsuarioException ex, Model model, HttpServletRequest request) {
        // Se for requisição REST, retorna ProblemDetail
        if (isRestRequest(request)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());
            problemDetail.setTitle("Erro de Validação - Usuário");
            problemDetail.setType(URI.create("https://clube-livro.com/errors/usuario-validation"));
            problemDetail.setProperty("timestamp", Instant.now());
            problemDetail.setProperty("category", "USUARIO");
            return ResponseEntity.badRequest().body(problemDetail);
        }
        
        // Se for requisição MVC, retorna view
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
     * Trata exceções específicas de livros
     */
    @ExceptionHandler(LivroException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleLivroException(LivroException ex, Model model, HttpServletRequest request) {
        // Se for requisição REST, retorna ProblemDetail
        if (isRestRequest(request)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());
            problemDetail.setTitle("Erro de Validação - Livro");
            problemDetail.setType(URI.create("https://clube-livro.com/errors/livro-validation"));
            problemDetail.setProperty("timestamp", Instant.now());
            problemDetail.setProperty("category", "LIVRO");
            return ResponseEntity.badRequest().body(problemDetail);
        }
        
        // Se for requisição MVC, retorna view
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

    @ExceptionHandler(GrupoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleGrupoException(GrupoException ex, Model model, HttpServletRequest request) {
        // Se for requisição REST, retorna ProblemDetail
        if (isRestRequest(request)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());
            problemDetail.setTitle("Erro de Validação - Grupo");
            problemDetail.setType(URI.create("https://clube-livro.com/errors/grupo-validation"));
            problemDetail.setProperty("timestamp", Instant.now());
            problemDetail.setProperty("category", "GRUPO");
            return ResponseEntity.badRequest().body(problemDetail);
        }
        
        // Se for requisição MVC, retorna view
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
    public Object handleGrupoLivroException(GrupoLivroException ex, Model model, HttpServletRequest request) {
        // Se for requisição REST, retorna ProblemDetail
        if (isRestRequest(request)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());
            problemDetail.setTitle("Erro de Validação - Associação Grupo-Livro");
            problemDetail.setType(URI.create("https://clube-livro.com/errors/grupo-livro-validation"));
            problemDetail.setProperty("timestamp", Instant.now());
            problemDetail.setProperty("category", "GRUPO_LIVRO");
            return ResponseEntity.badRequest().body(problemDetail);
        }
        
        // Se for requisição MVC, retorna view
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
    public Object handleEncontroException(EncontroException ex, Model model, HttpServletRequest request) {
        // Se for requisição REST, retorna ProblemDetail
        if (isRestRequest(request)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());
            problemDetail.setTitle("Erro de Validação - Encontro");
            problemDetail.setType(URI.create("https://clube-livro.com/errors/encontro-validation"));
            problemDetail.setProperty("timestamp", Instant.now());
            problemDetail.setProperty("category", "ENCONTRO");
            return ResponseEntity.badRequest().body(problemDetail);
        }
        
        // Se for requisição MVC, retorna view
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
    public Object handleUsuarioGrupoException(UsuarioGrupoException ex, Model model, HttpServletRequest request) {
        // Se for requisição REST, retorna ProblemDetail
        if (isRestRequest(request)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());
            problemDetail.setTitle("Erro de Validação - Associação Usuário-Grupo");
            problemDetail.setType(URI.create("https://clube-livro.com/errors/usuario-grupo-validation"));
            problemDetail.setProperty("timestamp", Instant.now());
            problemDetail.setProperty("category", "USUARIO_GRUPO");
            return ResponseEntity.badRequest().body(problemDetail);
        }
        
        // Se for requisição MVC, retorna view
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
    public Object handleClubeLivroException(ClubeLivroException ex, Model model, HttpServletRequest request) {
        // Se for requisição REST, retorna ProblemDetail
        if (isRestRequest(request)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
            problemDetail.setTitle("Erro Interno do Sistema");
            problemDetail.setType(URI.create("https://clube-livro.com/errors/internal-error"));
            problemDetail.setProperty("timestamp", Instant.now());
            problemDetail.setProperty("category", "INTERNAL");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
        }
        
        // Se for requisição MVC, retorna view
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
    public Object handleGenericException(Exception ex, Model model, HttpServletRequest request) {
        // Se for requisição REST, retorna ProblemDetail
        if (isRestRequest(request)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Erro inesperado no servidor. Nossa equipe foi notificada.");
            problemDetail.setTitle("Erro Interno Inesperado");
            problemDetail.setType(URI.create("https://clube-livro.com/errors/unexpected"));
            problemDetail.setProperty("timestamp", Instant.now());
            problemDetail.setProperty("category", "UNEXPECTED");
            problemDetail.setProperty("originalMessage", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
        }
        
        // Se for requisição MVC, retorna view
        model.addAttribute("titulo", "Erro Inesperado");
        model.addAttribute("mensagem", "Ops! Algo deu errado.");
        model.addAttribute("detalhes", "Ocorreu um erro inesperado. Nossa equipe foi notificada e está trabalhando para resolver.");
        model.addAttribute("codigoErro", "500");
        model.addAttribute("voltarPara", "/");
        model.addAttribute("textoBotao", "Voltar ao Início");
        return "erro/erro-geral";
    }

    /**
     * Trata RuntimeExceptions genéricas (principalmente recursos não encontrados)
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleRuntimeException(RuntimeException ex, Model model, HttpServletRequest request) {
        // Se for requisição REST, retorna ProblemDetail
        if (isRestRequest(request)) {
            // Verifica se é um erro de "não encontrado" baseado na mensagem
            if (ex.getMessage() != null && 
                (ex.getMessage().toLowerCase().contains("não encontrado") ||
                ex.getMessage().toLowerCase().contains("not found"))) {
                ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatus.NOT_FOUND, ex.getMessage());
                problemDetail.setTitle("Recurso Não Encontrado");
                problemDetail.setType(URI.create("https://clube-livro.com/errors/not-found"));
                problemDetail.setProperty("timestamp", Instant.now());
                problemDetail.setProperty("category", "NOT_FOUND");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
            }

            // Para outras RuntimeExceptions, retorna erro genérico
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Erro na requisição: " + ex.getMessage());
            problemDetail.setTitle("Erro na Requisição");
            problemDetail.setType(URI.create("https://clube-livro.com/errors/bad-request"));
            problemDetail.setProperty("timestamp", Instant.now());
            problemDetail.setProperty("category", "BAD_REQUEST");
            return ResponseEntity.badRequest().body(problemDetail);
        }
        
        // Se for requisição MVC, usa o comportamento padrão das exceções já tratadas
        // Não tratamos RuntimeException genérica no MVC para não interferir com outras exceções
        throw ex;
    }

    /**
     * Trata erro 404 - Página não encontrada
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object handleNotFound(NoHandlerFoundException ex, Model model, HttpServletRequest request) {
        // Se for requisição REST, retorna ProblemDetail
        if (isRestRequest(request)) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, "Endpoint não encontrado: " + ex.getRequestURL());
            problemDetail.setTitle("Endpoint Não Encontrado");
            problemDetail.setType(URI.create("https://clube-livro.com/errors/endpoint-not-found"));
            problemDetail.setProperty("timestamp", Instant.now());
            problemDetail.setProperty("category", "NOT_FOUND");
            problemDetail.setProperty("method", ex.getHttpMethod());
            problemDetail.setProperty("path", ex.getRequestURL());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
        }
        
        // Se for requisição MVC, retorna view
        model.addAttribute("titulo", "Página Não Encontrada");
        model.addAttribute("mensagem", "A página que você procura não foi encontrada.");
        model.addAttribute("detalhes", "Verifique se o endereço está correto ou use o menu para navegar.");
        model.addAttribute("codigoErro", "404");
        model.addAttribute("voltarPara", "/");
        model.addAttribute("textoBotao", "Voltar ao Início");
        return "erro/erro-geral";
    }
}