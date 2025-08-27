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
@Table(name = "acao_administrativa")
public class AcaoAdministrativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acao_administrativa")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_admin")
    private Usuario admin;

    @ManyToOne
    @JoinColumn(name = "id_usuario_afetado")
    private Usuario usuarioAfetado;

    @Column(name = "acao", length = 100)
    private String acao;

    @Column(name = "data")
    private LocalDateTime data;

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getAdmin() {
        return admin;
    }

    public void setAdmin(Usuario admin) {
        this.admin = admin;
    }

    public Usuario getUsuarioAfetado() {
        return usuarioAfetado;
    }

    public void setUsuarioAfetado(Usuario usuarioAfetado) {
        this.usuarioAfetado = usuarioAfetado;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
