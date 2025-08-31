package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.dto.GrupoLivroDTO;
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

    // Métodos para trabalhar com DTO
    public List<GrupoLivroDTO> listarTodosDTO() {
        return grupoLivros.stream()
                .map(this::converterParaDTO)
                .toList();
    }
    
    public GrupoLivroDTO buscarPorIdDTO(Integer id) {
        GrupoLivro grupoLivro = buscarPorId(id);
        return grupoLivro != null ? converterParaDTO(grupoLivro) : null;
    }
    
    public List<GrupoLivroDTO> buscarPorGrupoDTO(Grupo grupo) {
        return buscarPorGrupo(grupo).stream()
                .map(this::converterParaDTO)
                .toList();
    }
    
    private GrupoLivroDTO converterParaDTO(GrupoLivro grupoLivro) {
        return new GrupoLivroDTO(
            grupoLivro.getId(),
            grupoLivro.getObservacao(),
            grupoLivro.getGrupo().getId(),
            grupoLivro.getGrupo().getNome(),
            grupoLivro.getGrupo().getDescricao(),
            grupoLivro.getGrupo().getLider() != null ? grupoLivro.getGrupo().getLider().getNome() : "Sem líder",
            grupoLivro.getLivro().getId(),
            grupoLivro.getLivro().getTitulo(),
            grupoLivro.getLivro().getAutor(),
            grupoLivro.getLivro().getDescricao()
        );
    }
}
