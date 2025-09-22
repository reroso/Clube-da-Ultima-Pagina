package com.clubedolivro.clube_da_ultima_pagina.controller.restapi;

import com.clubedolivro.clube_da_ultima_pagina.entity.Perfil;
import com.clubedolivro.clube_da_ultima_pagina.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações CRUD da entidade Perfil
 * Endpoints simples e didáticos para aprendizado
 */
@RestController
@RequestMapping("/api/v1/perfis")
@Tag(name = "Perfis", description = "API para gerenciamento de perfis")
public class PerfilRestController {

    @Autowired
    private PerfilService perfilService;

    /**
     * Listar todos os perfis
     * GET /api/v1/perfis
     */
    @GetMapping
    @Operation(summary = "Listar todos os perfis", description = "Retorna uma lista com todos os perfis cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de perfis retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<Perfil>> listarTodos() {
        List<Perfil> perfis = perfilService.listarTodos();
        return ResponseEntity.ok(perfis);
    }

    /**
     * Buscar perfil por ID
     * GET /api/v1/perfis/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar perfil por ID", description = "Retorna um perfil específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Perfil> buscarPorId(@PathVariable Integer id) {
        try {
            Perfil perfil = perfilService.buscarPorId(id);
            return ResponseEntity.ok(perfil);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Criar novo perfil
     * POST /api/v1/perfis
     */
    @PostMapping
    @Operation(summary = "Criar novo perfil", description = "Cadastra um novo perfil no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perfil criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Perfil> criar(@RequestBody Perfil perfil) {
        try {
            Perfil novoPerfil = perfilService.salvar(perfil);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPerfil);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Atualizar perfil existente
     * PUT /api/v1/perfis/{id}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar perfil", description = "Atualiza os dados de um perfil existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Perfil> atualizar(@PathVariable Integer id, @RequestBody Perfil perfil) {
        try {
            Perfil perfilAtualizado = perfilService.atualizar(id, perfil);
            return ResponseEntity.ok(perfilAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Excluir perfil
     * DELETE /api/v1/perfis/{id}
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir perfil", description = "Remove um perfil do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        try {
            perfilService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar perfil por nome
     * GET /api/v1/perfis/buscar?nome={nome}
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar perfil por nome", description = "Retorna um perfil específico pelo nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Perfil> buscarPorNome(@RequestParam String nome) {
        try {
            Perfil perfil = perfilService.buscarPorNome(nome);
            if (perfil != null) {
                return ResponseEntity.ok(perfil);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}