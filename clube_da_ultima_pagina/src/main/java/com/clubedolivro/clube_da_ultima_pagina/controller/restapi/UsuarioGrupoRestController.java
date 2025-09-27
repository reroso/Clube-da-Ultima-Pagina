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

@RestController
@RequestMapping("/api/v1/usuario-grupos")
@Tag(name = "Usuario-Grupos", description = "API para gerenciamento de participações de usuários em grupos")
public class UsuarioGrupoRestController {

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    @GetMapping
    @Operation(summary = "Listar todas as associações usuário-grupo", description = "Retorna lista com todas as participações cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de associações retornada com sucesso")
    })
    public ResponseEntity<List<UsuarioGrupo>> listarTodos() {
        List<UsuarioGrupo> usuarioGrupos = usuarioGrupoService.listarTodos();
        return ResponseEntity.ok(usuarioGrupos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar associação por ID", description = "Retorna uma associação específica pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação encontrada"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<UsuarioGrupo> buscarPorId(@PathVariable Integer id) {
        UsuarioGrupo usuarioGrupo = usuarioGrupoService.buscarPorId(id);
        return ResponseEntity.ok(usuarioGrupo);
    }

    @PostMapping
    @Operation(summary = "Criar nova associação usuário-grupo", description = "Cria uma nova participação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Associação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<UsuarioGrupo> criar(@RequestBody UsuarioGrupo usuarioGrupo) {
            UsuarioGrupo novoUsuarioGrupo = usuarioGrupoService.salvar(usuarioGrupo);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuarioGrupo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar associação usuário-grupo", description = "Atualiza dados de uma participação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Associação atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<UsuarioGrupo> atualizar(@PathVariable Integer id, @RequestBody UsuarioGrupo usuarioGrupo) {
        UsuarioGrupo usuarioGrupoAtualizado = usuarioGrupoService.atualizar(id, usuarioGrupo);
        return ResponseEntity.ok(usuarioGrupoAtualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir associação usuário-grupo", description = "Remove uma participação do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Associação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Associação não encontrada")
    })
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        usuarioGrupoService.sairDoGrupo(id);
        return ResponseEntity.noContent().build();
    }
}