package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Encontro;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EncontroService {
    private static final List<Encontro> encontros = new ArrayList<>();
    private static int nextId = 1;

    public Encontro salvar(Encontro encontro) {
        if (encontro.getId() == null) {
            encontro.setId(nextId++);
        }
        encontros.add(encontro);
        return encontro;
    }

    public List<Encontro> listarTodos() {
        return Collections.unmodifiableList(encontros);
    }

    public Encontro buscarPorId(Integer id) {
        return encontros.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
