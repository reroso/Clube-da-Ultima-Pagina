package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.UsuarioGrupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Perfil;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GrupoService {
    private static final List<Grupo> grupos = new ArrayList<>();
    private static int nextId = 1;

    private final UsuarioGrupoService usuarioGrupoService;
    private final PerfilService perfilService;

    public GrupoService(UsuarioGrupoService usuarioGrupoService, PerfilService perfilService) {
        this.usuarioGrupoService = usuarioGrupoService;
        this.perfilService = perfilService;
    }

    public Grupo salvar(Grupo grupo) {
        if (grupo.getId() == null) {
            grupo.setId(nextId++);
        }
        grupos.add(grupo);

        // Se o grupo tem um líder, criar a associação UsuarioGrupo
        if (grupo.getLider() != null) {
            Perfil perfilLider = perfilService.buscarPorNome("Líder");
            if (perfilLider == null) {
                throw new RuntimeException("Perfil 'Líder' não encontrado");
            }

            UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
            usuarioGrupo.setUsuario(grupo.getLider());
            usuarioGrupo.setGrupo(grupo);
            usuarioGrupo.setPerfil(perfilLider);
            usuarioGrupoService.salvar(usuarioGrupo);
        }

        return grupo;
    }

    public List<Grupo> listarTodos() {
        return Collections.unmodifiableList(grupos);
    }

    public Grupo buscarPorId(Integer id) {
        return grupos.stream()
                .filter(g -> g.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
