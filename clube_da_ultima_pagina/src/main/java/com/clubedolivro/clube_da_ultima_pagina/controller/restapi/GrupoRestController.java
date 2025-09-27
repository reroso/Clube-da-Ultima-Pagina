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

@RestController
@RequestMapping("/api/v1/grupos")
@Tag(name = "Grupos", description = "API para gerenciamento de grupos")
public class GrupoRestController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    @Operation(summary = "Listar todos os grupos", description = "Retorna lista com todos os grupos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de grupos retornada com sucesso")
    })
    public ResponseEntity<List<Grupo>> listarTodos() {
        List<Grupo> grupos = grupoService.listarTodos();
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar grupo por ID", description = "Retorna um grupo específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grupo encontrado"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    public ResponseEntity<Grupo> buscarPorId(@PathVariable Integer id) {
        Grupo grupo = grupoService.buscarPorId(id);
        return ResponseEntity.ok(grupo);
    }

    @PostMapping
    @Operation(summary = "Criar novo grupo", description = "Cria um novo grupo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grupo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Grupo> criar(@RequestBody Grupo grupo) {
        Grupo novoGrupo = grupoService.salvar(grupo);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoGrupo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar grupo", description = "Atualiza dados de um grupo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grupo atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    public ResponseEntity<Grupo> atualizar(@PathVariable Integer id, @RequestBody Grupo grupo) {
        Grupo grupoAtualizado = grupoService.atualizar(id, grupo);
        return ResponseEntity.ok(grupoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir grupo", description = "Remove um grupo do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Grupo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        grupoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}