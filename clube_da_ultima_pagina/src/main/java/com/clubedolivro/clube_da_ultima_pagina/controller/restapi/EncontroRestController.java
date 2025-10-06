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

@RestController
@RequestMapping("/api/v1/encontros")
@Tag(name = "Encontros", description = "API para gerenciamento de encontros")
public class EncontroRestController {

    @Autowired
    private EncontroService encontroService;

    @GetMapping
    @Operation(summary = "Listar todos os encontros", description = "Retorna lista com todos os encontros cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de encontros retornada com sucesso")
    })
    public ResponseEntity<List<Encontro>> listarTodos() {
        List<Encontro> encontros = encontroService.listarTodos();
        return ResponseEntity.ok(encontros);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar encontro por ID", description = "Retorna um encontro específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encontro encontrado"),
            @ApiResponse(responseCode = "404", description = "Encontro não encontrado")
    })
    public ResponseEntity<Encontro> buscarPorId(@PathVariable Integer id) {
        Encontro encontro = encontroService.buscarPorId(id);
        return ResponseEntity.ok(encontro);
    }

    @PostMapping
    @Operation(summary = "Criar novo encontro", description = "Cria um novo encontro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Encontro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Encontro> criar(@RequestBody Encontro encontro) {
        Encontro novoEncontro = encontroService.salvar(encontro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEncontro);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar encontro", description = "Atualiza dados de um encontro existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Encontro atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Encontro não encontrado")
    })
    public ResponseEntity<Encontro> atualizar(@PathVariable Integer id, @RequestBody Encontro encontro) {
        Encontro encontroAtualizado = encontroService.atualizar(id, encontro);
        return ResponseEntity.ok(encontroAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir encontro", description = "Remove um encontro do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Encontro excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Encontro não encontrado")
    })
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        encontroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}