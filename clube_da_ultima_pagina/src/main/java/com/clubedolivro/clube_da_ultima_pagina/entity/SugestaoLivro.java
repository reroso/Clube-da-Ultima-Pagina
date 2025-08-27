package com.clubedolivro.clube_da_ultima_pagina.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sugestao_livro")
public class SugestaoLivro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sugestao_livro")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "id_livro")
    private Livro livro;

    @Column(name = "data_sugestao")
    private LocalDateTime dataSugestao;

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDateTime getDataSugestao() {
        return dataSugestao;
    }

    public void setDataSugestao(LocalDateTime dataSugestao) {
        this.dataSugestao = dataSugestao;
    }
}
