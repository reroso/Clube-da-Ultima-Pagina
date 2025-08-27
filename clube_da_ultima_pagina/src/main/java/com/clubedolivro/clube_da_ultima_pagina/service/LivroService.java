package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Livro;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LivroService {
    private static final List<Livro> livros = new ArrayList<>();
    private static int nextId = 1;

    public Livro salvar(Livro livro) {
        if (livro.getId() == null) {
            livro.setId(nextId++);
        }
        livros.add(livro);
        return livro;
    }

    public List<Livro> listarTodos() {
        return Collections.unmodifiableList(livros);
    }

    public Livro buscarPorId(Integer id) {
        return livros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void excluir(Integer id) {
        livros.removeIf(l -> l.getId().equals(id));
    }
}
