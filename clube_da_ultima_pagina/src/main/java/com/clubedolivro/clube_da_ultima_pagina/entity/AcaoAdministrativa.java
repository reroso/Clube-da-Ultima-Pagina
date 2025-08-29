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
import lombok.Data;

@Entity
@Table(name = "acao_administrativa")
@Data
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
}
