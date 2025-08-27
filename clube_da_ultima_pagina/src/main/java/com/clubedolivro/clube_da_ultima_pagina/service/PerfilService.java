package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Perfil;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PerfilService {
    private static final List<Perfil> perfis = new ArrayList<>();
    private static int nextId = 1;

    public PerfilService() {
        // Inicializa os perfis básicos se ainda não existirem
        if (perfis.isEmpty()) {
            criarPerfilSeNaoExiste("Membro");
            criarPerfilSeNaoExiste("Líder");
            criarPerfilSeNaoExiste("Administrador");
        }
    }

    private void criarPerfilSeNaoExiste(String nome) {
        if (buscarPorNome(nome) == null) {
            Perfil perfil = new Perfil();
            perfil.setId(nextId++);
            perfil.setNome(nome);
            perfis.add(perfil);
        }
    }

    public List<Perfil> listarTodos() {
        return Collections.unmodifiableList(perfis);
    }

    public Perfil buscarPorNome(String nome) {
        return perfis.stream()
                .filter(p -> p.getNome().equals(nome))
                .findFirst()
                .orElse(null);
    }

    public Perfil salvar(Perfil perfil) {
        if (perfil.getId() == null) {
            perfil.setId(nextId++);
        }
        perfis.add(perfil);
        return perfil;
    }
}
