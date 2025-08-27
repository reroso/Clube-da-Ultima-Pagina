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
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "encontro")
public class Encontro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_encontro")
    private Integer id;

    @NotNull(message = "Grupo é obrigatório")
    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    @NotNull(message = "Data e hora são obrigatórias")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
