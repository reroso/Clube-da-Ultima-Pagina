package com.clubedolivro.clube_da_ultima_pagina.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrupoLivroDTO {
    private Integer id;
    private String observacao;
    
    // Dados do Grupo
    private Integer grupoId;
    private String grupoNome;
    private String grupoDescricao;
    private String liderNome;
    
    // Dados do Livro
    private Integer livroId;
    private String livroTitulo;
    private String livroAutor;
    private String livroDescricao;
    
    // Métodos auxiliares para exibição
    public String getGrupoCompleto() {
        return grupoNome + " (Líder: " + liderNome + ")";
    }
    
    public String getLivroCompleto() {
        return livroTitulo + " - " + livroAutor;
    }
}
