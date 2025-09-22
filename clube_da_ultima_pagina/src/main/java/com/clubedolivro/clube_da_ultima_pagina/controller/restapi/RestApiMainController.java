package com.clubedolivro.clube_da_ultima_pagina.controller.restapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1")
@Tag(name = "API Principal", description = "Informações gerais sobre a API REST")
public class RestApiMainController {

    @GetMapping()
    @Operation(summary = "Informações da API", description = "Retorna informações sobre os endpoints disponíveis na API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informações retornadas com sucesso")
    })
    public ResponseEntity<Map<String, Object>> getApiHome() {
        Map<String, Object> apiInfo = new HashMap<>();
        
        apiInfo.put("nome", "Clube da Última Página - REST API");
        apiInfo.put("versao", "1.0.0");
        apiInfo.put("descricao", "API REST para gerenciamento do sistema Clube da Última Página");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("Livros", "/api/v1/livros");
        endpoints.put("Usuários", "/api/v1/usuarios");
        endpoints.put("Grupos", "/api/v1/grupos");
        endpoints.put("Associações Usuário-Grupo", "/api/v1/usuario-grupos");
        endpoints.put("Encontros", "/api/v1/encontros");
        endpoints.put("Associações Grupo-Livro", "/api/v1/grupo-livros");
        
        apiInfo.put("endpoints", endpoints);
        apiInfo.put("documentacao", "/swagger-ui.html");
        
        return ResponseEntity.ok(apiInfo);
    }
}
