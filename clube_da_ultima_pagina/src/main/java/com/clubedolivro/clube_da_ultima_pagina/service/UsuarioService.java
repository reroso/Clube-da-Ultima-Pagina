package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioService {
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    public List<Usuario> listarTodos() {
        return Collections.unmodifiableList(usuarios);
    }

    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(idGenerator.getAndIncrement());
        }
        usuarios.add(usuario);
        return usuario;
    }
}
