package com.clubedolivro.clube_da_ultima_pagina.entity;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id;
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    
    @Email(message = "Email deve ter formato válido")
    @NotBlank(message = "Email é obrigatório")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    @Column(name = "senha", nullable = false, length = 255)
    private String senha;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private PerfilEnum perfil;
    
    @OneToMany(mappedBy = "usuario")
    private List<UsuarioGrupo> grupos;
    
    @OneToMany(mappedBy = "admin")
    private List<AcaoAdministrativa> acoesRealizadas;
    
    @OneToMany(mappedBy = "usuarioAfetado")
    private List<AcaoAdministrativa> acoesRecebidas;
    
    @OneToMany(mappedBy = "usuario")
    private List<Voto> votos;
}
