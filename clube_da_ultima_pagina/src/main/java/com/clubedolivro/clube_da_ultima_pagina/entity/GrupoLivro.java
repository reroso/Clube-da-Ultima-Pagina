package com.clubedolivro.clube_da_ultima_pagina.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "grupo_livro")
@Data
public class GrupoLivro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo_livro")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_grupo", nullable = false)
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "id_livro", nullable = false)
    private Livro livro;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;
}
