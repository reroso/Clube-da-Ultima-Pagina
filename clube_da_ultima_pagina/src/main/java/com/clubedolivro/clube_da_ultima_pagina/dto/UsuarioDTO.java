package com.clubedolivro.clube_da_ultima_pagina.dto;

import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;

public class UsuarioDTO {
    private Integer id;
    private String nome;
    private String email;
    private String perfil;

    public UsuarioDTO(Usuario usuario, String perfil) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.perfil = perfil;
    }

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

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
