package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.GrupoLivro;
import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Livro;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GrupoLivroService {
    private static final List<GrupoLivro> grupoLivros = new ArrayList<>();
    private static int nextId = 1;

    public GrupoLivro salvar(GrupoLivro grupoLivro) {
        if (grupoLivro.getId() == null) {
            grupoLivro.setId(nextId++);
        }
        grupoLivros.add(grupoLivro);
        return grupoLivro;
    }

    public List<GrupoLivro> listarTodos() {
        return Collections.unmodifiableList(grupoLivros);
    }

    public GrupoLivro buscarPorId(Integer id) {
        return grupoLivros.stream()
                .filter(gl -> gl.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<GrupoLivro> buscarPorGrupo(Grupo grupo) {
        return grupoLivros.stream()
                .filter(gl -> gl.getGrupo().getId().equals(grupo.getId()))
                .toList();
    }

    public List<GrupoLivro> buscarPorLivro(Livro livro) {
        return grupoLivros.stream()
                .filter(gl -> gl.getLivro().getId().equals(livro.getId()))
                .toList();
    }

    public boolean existeAssociacao(Integer grupoId, Integer livroId) {
        return grupoLivros.stream()
                .anyMatch(gl -> gl.getGrupo().getId().equals(grupoId) && 
                               gl.getLivro().getId().equals(livroId));
    }
}
