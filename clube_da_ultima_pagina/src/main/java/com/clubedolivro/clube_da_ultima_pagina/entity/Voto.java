package com.clubedolivro.clube_da_ultima_pagina.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "voto",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_sugestao_livro", "id_usuario"})
)
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voto")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_sugestao_livro")
    private SugestaoLivro sugestaoLivro;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "voto")
    private Boolean voto;

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SugestaoLivro getSugestaoLivro() {
        return sugestaoLivro;
    }

    public void setSugestaoLivro(SugestaoLivro sugestaoLivro) {
        this.sugestaoLivro = sugestaoLivro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getVoto() {
        return voto;
    }

    public void setVoto(Boolean voto) {
        this.voto = voto;
    }
}
