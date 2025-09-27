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

@RestController
@RequestMapping("/api/v1/grupo-livros")
@Tag(name = "Grupo-Livros", description = "API para gerenciamento de associações grupo-livro")
public class GrupoLivroRestController {

    @Autowired
    private GrupoLivroService grupoLivroService;

    @GetMapping
    @Operation(summary = "Listar todas as associações grupo-livro", description = "Retorna lista com todas as associações grupo-livro cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de associações retornada com sucesso")
    })
    public ResponseEntity<List<GrupoLivro>> listarTodos() {
        List<GrupoLivro> grupoLivros = grupoLivroService.listarTodos();
        return ResponseEntity.ok(grupoLivros);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar associação grupo-livro por ID", description = "Retorna uma associação específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação encontrada"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<GrupoLivro> buscarPorId(@PathVariable Integer id) {
        GrupoLivro grupoLivro = grupoLivroService.buscarPorId(id);
        return ResponseEntity.ok(grupoLivro);
    }

    @PostMapping
    @Operation(summary = "Criar nova associação grupo-livro", description = "Cria uma nova associação grupo-livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Associação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<GrupoLivro> criar(@RequestBody GrupoLivro grupoLivro) {
        GrupoLivro novoGrupoLivro = grupoLivroService.salvar(grupoLivro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoGrupoLivro);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar associação grupo-livro", description = "Atualiza dados de uma associação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<GrupoLivro> atualizar(@PathVariable Integer id, @RequestBody GrupoLivro grupoLivro) {
        GrupoLivro grupoLivroAtualizado = grupoLivroService.atualizar(id, grupoLivro);
        return ResponseEntity.ok(grupoLivroAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir associação grupo-livro", description = "Remove uma associação do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Associação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        grupoLivroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}