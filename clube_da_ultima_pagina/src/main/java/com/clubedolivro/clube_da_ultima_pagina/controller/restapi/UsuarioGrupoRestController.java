package com.clubedolivro.clube_da_ultima_pagina.controller.restapi;

import com.clubedolivro.clube_da_ultima_pagina.entity.UsuarioGrupo;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioGrupoService;
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
 * Controlador REST para operações CRUD da entidade UsuarioGrupo
 * Endpoints simples e didáticos para aprendizado
 */
@RestController
@RequestMapping("/api/v1/usuario-grupos")
@Tag(name = "UsuarioGrupos", description = "API para gerenciamento de associações usuário-grupo")
public class UsuarioGrupoRestController {

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    /**
     * Listar todas as associações usuário-grupo
     * GET /api/v1/usuario-grupos
     */
    @GetMapping
    @Operation(summary = "Listar todas as associações", description = "Retorna uma lista com todas as associações usuário-grupo cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de associações retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<UsuarioGrupo>> listarTodos() {
        List<UsuarioGrupo> associacoes = usuarioGrupoService.listarTodos();
        return ResponseEntity.ok(associacoes);
    }

    /**
     * Buscar associação por ID
     * GET /api/v1/usuario-grupos/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar associação por ID", description = "Retorna uma associação específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação encontrada"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioGrupo> buscarPorId(@PathVariable Integer id) {
        try {
            UsuarioGrupo usuarioGrupo = usuarioGrupoService.buscarPorId(id);
            return ResponseEntity.ok(usuarioGrupo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Criar nova associação usuário-grupo
     * POST /api/v1/usuario-grupos
     */
    @PostMapping
    @Operation(summary = "Criar nova associação", description = "Cadastra uma nova associação usuário-grupo no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Associação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioGrupo> criar(@RequestBody UsuarioGrupo usuarioGrupo) {
        try {
            UsuarioGrupo novaAssociacao = usuarioGrupoService.salvar(usuarioGrupo);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaAssociacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Atualizar associação existente
     * PUT /api/v1/usuario-grupos/{id}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar associação", description = "Atualiza os dados de uma associação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioGrupo> atualizar(@PathVariable Integer id, @RequestBody UsuarioGrupo usuarioGrupo) {
        try {
            UsuarioGrupo associacaoAtualizada = usuarioGrupoService.atualizar(id, usuarioGrupo);
            return ResponseEntity.ok(associacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Excluir associação
     * DELETE /api/v1/usuario-grupos/{id}
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir associação", description = "Remove uma associação do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Associação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        try {
            usuarioGrupoService.sairDoGrupo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar associações por usuário
     * GET /api/v1/usuario-grupos/usuario/{usuarioId}
     */
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Buscar associações por usuário", description = "Retorna todas as associações de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de associações encontradas"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<UsuarioGrupo>> buscarPorUsuario(@PathVariable Integer usuarioId) {
        try {
            List<UsuarioGrupo> associacoes = usuarioGrupoService.buscarAssociacoesPorUsuario(usuarioId);
            return ResponseEntity.ok(associacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Buscar associações por grupo
     * GET /api/v1/usuario-grupos/grupo/{grupoId}
     */
    @GetMapping("/grupo/{grupoId}")
    @Operation(summary = "Buscar associações por grupo", description = "Retorna todas as associações de um grupo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de associações encontradas"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<UsuarioGrupo>> buscarPorGrupo(@PathVariable Integer grupoId) {
        try {
            List<UsuarioGrupo> associacoes = usuarioGrupoService.buscarAssociacoesPorGrupo(grupoId);
            return ResponseEntity.ok(associacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Buscar líderes
     * GET /api/v1/usuario-grupos/lideres
     */
    @GetMapping("/lideres")
    @Operation(summary = "Buscar líderes", description = "Retorna todas as associações onde o perfil é Líder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de líderes encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<UsuarioGrupo>> buscarLideres() {
        try {
            List<UsuarioGrupo> lideres = usuarioGrupoService.buscarLideres();
            return ResponseEntity.ok(lideres);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Buscar membros
     * GET /api/v1/usuario-grupos/membros
     */
    @GetMapping("/membros")
    @Operation(summary = "Buscar membros", description = "Retorna todas as associações onde o perfil é Membro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de membros encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<UsuarioGrupo>> buscarMembros() {
        try {
            List<UsuarioGrupo> membros = usuarioGrupoService.buscarMembros();
            return ResponseEntity.ok(membros);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}