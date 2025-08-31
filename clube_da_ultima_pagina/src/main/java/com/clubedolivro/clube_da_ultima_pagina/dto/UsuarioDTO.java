package com.clubedolivro.clube_da_ultima_pagina.dto;

import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private String perfil;

    // Construtor vazio para form binding
    public UsuarioDTO() {}

    public UsuarioDTO(Usuario usuario, String perfil) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.perfil = perfil;
    }

    // Método para criar DTO sem senha (para listagem)
    public static UsuarioDTO semSenha(Usuario usuario, String perfil) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setPerfil(perfil);
        // senha fica null
        return dto;
    }

    // Método para limpar senha após processamento (segurança)
    public void limparSenha() {
        this.senha = null;
    }
}
