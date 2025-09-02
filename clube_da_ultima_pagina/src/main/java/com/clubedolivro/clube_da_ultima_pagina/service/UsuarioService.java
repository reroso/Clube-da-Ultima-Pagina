package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.dto.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UsuarioService {
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final AtomicInteger idGenerator = new AtomicInteger(1);

    public List<Usuario> listarTodos() {
        return Collections.unmodifiableList(usuarios);
    }

    // ✅ MÉTODO PRINCIPAL: Recebe DTO e converte para Entity
    public Usuario criarUsuario(UsuarioDTO dto) {
        // Conversão DTO → Entity
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha()); // Por enquanto sem criptografia
        
        return salvar(usuario);
    }

    // ✅ MÉTODO PARA LISTAGEM: Retorna DTOs sem senha
    public List<UsuarioDTO> listarUsuariosDTO() {
        List<UsuarioDTO> resultado = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            // Usando método seguro (sem senha)
            String perfil = "Membro"; 
            UsuarioDTO dto = UsuarioDTO.semSenha(usuario, perfil);
            resultado.add(dto);
        }
        return resultado;
    }

    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(idGenerator.getAndIncrement());
        }
        usuarios.add(usuario);
        return usuario;
    }

    public Usuario buscarPorId(Integer id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
