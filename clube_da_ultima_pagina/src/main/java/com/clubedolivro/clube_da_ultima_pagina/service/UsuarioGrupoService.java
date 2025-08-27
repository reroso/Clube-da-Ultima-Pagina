package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.entity.UsuarioGrupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Perfil;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UsuarioGrupoService {
    private static final List<UsuarioGrupo> usuarioGrupos = new ArrayList<>();
    private static int nextId = 1;

    private final PerfilService perfilService;

    public UsuarioGrupoService(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    public UsuarioGrupo salvar(UsuarioGrupo usuarioGrupo) {
        if (usuarioGrupo.getId() == null) {
            usuarioGrupo.setId(nextId++);
        }
        usuarioGrupos.add(usuarioGrupo);
        return usuarioGrupo;
    }

    public List<UsuarioGrupo> listarTodos() {
        return Collections.unmodifiableList(usuarioGrupos);
    }

    public UsuarioGrupo buscarPorUsuario(Integer idUsuario) {
        return usuarioGrupos.stream()
                .filter(ug -> ug.getUsuario().getId().equals(idUsuario))
                .findFirst()
                .orElse(null);
    }

    public boolean existeAssociacao(Integer idUsuario, Integer idGrupo) {
        return usuarioGrupos.stream()
                .anyMatch(ug -> 
                    ug.getUsuario() != null && 
                    ug.getGrupo() != null && 
                    ug.getUsuario().getId().equals(idUsuario) && 
                    ug.getGrupo().getId().equals(idGrupo)
                );
    }

    public UsuarioGrupo associarUsuarioMembro(Usuario usuario) {
        Perfil perfilMembro = perfilService.buscarPorNome("Membro");
        if (perfilMembro == null) {
            throw new RuntimeException("Perfil 'Membro' não encontrado");
        }

        UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
        usuarioGrupo.setUsuario(usuario);
        usuarioGrupo.setPerfil(perfilMembro);
        // grupo é null inicialmente
        
        return salvar(usuarioGrupo);
    }
}
