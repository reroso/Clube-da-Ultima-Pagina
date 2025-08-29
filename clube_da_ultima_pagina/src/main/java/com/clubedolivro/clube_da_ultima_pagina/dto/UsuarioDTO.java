package com.clubedolivro.clube_da_ultima_pagina.dto;

import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import lombok.Data;

@Data
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
}
