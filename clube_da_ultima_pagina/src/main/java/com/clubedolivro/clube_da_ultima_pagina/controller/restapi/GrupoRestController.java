package com.clubedolivro.clube_da_ultima_pagina.controller.restapi;

import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoService;
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
 * Controlador REST para operações CRUD da entidade Grupo
 * Endpoints simples e didáticos para aprendizado
 */
@RestController
@RequestMapping("/api/v1/grupos")
@Tag(name = "Grupos", description = "API para gerenciamento de grupos")
public class GrupoRestController {

    @Autowired
    private GrupoService grupoService;

    /**
     * Listar todos os grupos
     * GET /api/v1/grupos
     */
    @GetMapping
    @Operation(summary = "Listar todos os grupos", description = "Retorna uma lista com todos os grupos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de grupos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<Grupo>> listarTodos() {
        List<Grupo> grupos = grupoService.listarTodos();
        return ResponseEntity.ok(grupos);
    }

    /**
     * Buscar grupo por ID
     * GET /api/v1/grupos/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar grupo por ID", description = "Retorna um grupo específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grupo encontrado"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Grupo> buscarPorId(@PathVariable Integer id) {
        try {
            Grupo grupo = grupoService.buscarPorId(id);
            return ResponseEntity.ok(grupo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Criar novo grupo
     * POST /api/v1/grupos
     */
    @PostMapping
    @Operation(summary = "Criar novo grupo", description = "Cadastra um novo grupo no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grupo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Grupo> criar(@RequestBody Grupo grupo) {
        try {
            Grupo novoGrupo = grupoService.salvar(grupo);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoGrupo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Atualizar grupo existente
     * PUT /api/v1/grupos/{id}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar grupo", description = "Atualiza os dados de um grupo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grupo atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Grupo> atualizar(@PathVariable Integer id, @RequestBody Grupo grupo) {
        try {
            Grupo grupoAtualizado = grupoService.atualizar(id, grupo);
            return ResponseEntity.ok(grupoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Excluir grupo
     * DELETE /api/v1/grupos/{id}
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir grupo", description = "Remove um grupo do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Grupo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        try {
            grupoService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar grupos por nome
     * GET /api/v1/grupos/buscar?nome={nome}
     */
    @GetMapping("/buscar")
    @Operation(summary = "Buscar grupos por nome", description = "Retorna uma lista de grupos que contenham o nome pesquisado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de grupos encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<Grupo>> buscarPorNome(@RequestParam String nome) {
        try {
            List<Grupo> grupos = grupoService.buscarPorNomeContendo(nome);
            return ResponseEntity.ok(grupos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}