package com.clubedolivro.clube_da_ultima_pagina.entity;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
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
    private List<CapituloLido> capitulosLidos;
    
    @OneToMany(mappedBy = "usuario")
    private List<Anotacao> anotacoes;
    
    @OneToMany(mappedBy = "usuario")
    private List<Voto> votos;

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilEnum getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }

    public List<UsuarioGrupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<UsuarioGrupo> grupos) {
        this.grupos = grupos;
    }

    public List<AcaoAdministrativa> getAcoesRealizadas() {
        return acoesRealizadas;
    }

    public void setAcoesRealizadas(List<AcaoAdministrativa> acoesRealizadas) {
        this.acoesRealizadas = acoesRealizadas;
    }

    public List<AcaoAdministrativa> getAcoesRecebidas() {
        return acoesRecebidas;
    }

    public void setAcoesRecebidas(List<AcaoAdministrativa> acoesRecebidas) {
        this.acoesRecebidas = acoesRecebidas;
    }

    public List<CapituloLido> getCapitulosLidos() {
        return capitulosLidos;
    }

    public void setCapitulosLidos(List<CapituloLido> capitulosLidos) {
        this.capitulosLidos = capitulosLidos;
    }

    public List<Anotacao> getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(List<Anotacao> anotacoes) {
        this.anotacoes = anotacoes;
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }
}
