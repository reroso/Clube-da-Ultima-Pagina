package com.clubedolivro.clube_da_ultima_pagina.controller.restapi;

import com.clubedolivro.clube_da_ultima_pagina.entity.Encontro;
import com.clubedolivro.clube_da_ultima_pagina.service.EncontroService;
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
 * Controlador REST para operações CRUD da entidade Encontro
 * Endpoints simples e didáticos para aprendizado
 */
@RestController
@RequestMapping("/api/v1/encontros")
@Tag(name = "Encontros", description = "API para gerenciamento de encontros")
public class EncontroRestController {

    @Autowired
    private EncontroService encontroService;

    /**
     * Listar todos os encontros
     * GET /api/v1/encontros
     */
    @GetMapping
    @Operation(summary = "Listar todos os encontros", description = "Retorna uma lista com todos os encontros cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de encontros retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<Encontro>> listarTodos() {
        List<Encontro> encontros = encontroService.listarTodos();
        return ResponseEntity.ok(encontros);
    }

    /**
     * Buscar encontro por ID
     * GET /api/v1/encontros/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar encontro por ID", description = "Retorna um encontro específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontro encontrado"),
            @ApiResponse(responseCode = "404", description = "Encontro não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Encontro> buscarPorId(@PathVariable Integer id) {
        try {
            Encontro encontro = encontroService.buscarPorId(id);
            return ResponseEntity.ok(encontro);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Criar novo encontro
     * POST /api/v1/encontros
     */
    @PostMapping
    @Operation(summary = "Criar novo encontro", description = "Cadastra um novo encontro no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Encontro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Encontro> criar(@RequestBody Encontro encontro) {
        try {
            Encontro novoEncontro = encontroService.salvar(encontro);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEncontro);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Atualizar encontro existente
     * PUT /api/v1/encontros/{id}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar encontro", description = "Atualiza os dados de um encontro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Encontro não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Encontro> atualizar(@PathVariable Integer id, @RequestBody Encontro encontro) {
        try {
            Encontro encontroAtualizado = encontroService.atualizar(id, encontro);
            return ResponseEntity.ok(encontroAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Excluir encontro
     * DELETE /api/v1/encontros/{id}
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir encontro", description = "Remove um encontro do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Encontro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Encontro não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        try {
            encontroService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar encontros por grupo
     * GET /api/v1/encontros/grupo/{grupoId}
     */
    @GetMapping("/grupo/{grupoId}")
    @Operation(summary = "Buscar encontros por grupo", description = "Retorna todos os encontros de um grupo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de encontros encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<Encontro>> buscarPorGrupo(@PathVariable Integer grupoId) {
        try {
            List<Encontro> encontros = encontroService.buscarPorGrupoId(grupoId);
            return ResponseEntity.ok(encontros);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}