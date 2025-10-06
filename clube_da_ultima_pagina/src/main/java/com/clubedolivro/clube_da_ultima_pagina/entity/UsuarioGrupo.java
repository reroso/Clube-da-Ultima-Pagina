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
import lombok.Data;
import lombok.ToString;

@Entity
@Table(
    name = "usuario_grupo",
    uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario", "id_grupo"})
)
@Data
public class UsuarioGrupo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_grupo")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @ToString.Exclude
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    @ToString.Exclude
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "id_perfil")
    @ToString.Exclude
    private Perfil perfil;
}
