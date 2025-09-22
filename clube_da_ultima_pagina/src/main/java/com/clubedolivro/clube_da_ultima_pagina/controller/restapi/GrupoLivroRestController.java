package com.clubedolivro.clube_da_ultima_pagina.controller.restapi;

import com.clubedolivro.clube_da_ultima_pagina.entity.GrupoLivro;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoLivroService;
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
 * Controlador REST para operações CRUD da entidade GrupoLivro
 * Endpoints simples e didáticos para aprendizado
 */
@RestController
@RequestMapping("/api/v1/grupo-livros")
@Tag(name = "GrupoLivros", description = "API para gerenciamento de associações grupo-livro")
public class GrupoLivroRestController {

    @Autowired
    private GrupoLivroService grupoLivroService;

    /**
     * Listar todas as associações grupo-livro
     * GET /api/v1/grupo-livros
     */
    @GetMapping
    @Operation(summary = "Listar todas as associações", description = "Retorna uma lista com todas as associações grupo-livro cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de associações retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<GrupoLivro>> listarTodos() {
        List<GrupoLivro> associacoes = grupoLivroService.listarTodos();
        return ResponseEntity.ok(associacoes);
    }

    /**
     * Buscar associação por ID
     * GET /api/v1/grupo-livros/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar associação por ID", description = "Retorna uma associação específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação encontrada"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<GrupoLivro> buscarPorId(@PathVariable Integer id) {
        try {
            GrupoLivro grupoLivro = grupoLivroService.buscarPorId(id);
            return ResponseEntity.ok(grupoLivro);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Criar nova associação grupo-livro
     * POST /api/v1/grupo-livros
     */
    @PostMapping
    @Operation(summary = "Criar nova associação", description = "Cadastra uma nova associação grupo-livro no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Associação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<GrupoLivro> criar(@RequestBody GrupoLivro grupoLivro) {
        try {
            GrupoLivro novaAssociacao = grupoLivroService.salvar(grupoLivro);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaAssociacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Atualizar associação existente
     * PUT /api/v1/grupo-livros/{id}
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar associação", description = "Atualiza os dados de uma associação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<GrupoLivro> atualizar(@PathVariable Integer id, @RequestBody GrupoLivro grupoLivro) {
        try {
            GrupoLivro associacaoAtualizada = grupoLivroService.atualizar(id, grupoLivro);
            return ResponseEntity.ok(associacaoAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Excluir associação
     * DELETE /api/v1/grupo-livros/{id}
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
            grupoLivroService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Buscar associações por grupo
     * GET /api/v1/grupo-livros/grupo/{grupoId}
     */
    @GetMapping("/grupo/{grupoId}")
    @Operation(summary = "Buscar associações por grupo", description = "Retorna todas as associações de um grupo específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de associações encontradas"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<GrupoLivro>> buscarPorGrupo(@PathVariable Integer grupoId) {
        try {
            List<GrupoLivro> associacoes = grupoLivroService.buscarPorGrupoId(grupoId);
            return ResponseEntity.ok(associacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Buscar associações por livro
     * GET /api/v1/grupo-livros/livro/{livroId}
     */
    @GetMapping("/livro/{livroId}")
    @Operation(summary = "Buscar associações por livro", description = "Retorna todas as associações de um livro específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de associações encontradas"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<GrupoLivro>> buscarPorLivro(@PathVariable Integer livroId) {
        try {
            List<GrupoLivro> associacoes = grupoLivroService.buscarPorLivroId(livroId);
            return ResponseEntity.ok(associacoes);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}