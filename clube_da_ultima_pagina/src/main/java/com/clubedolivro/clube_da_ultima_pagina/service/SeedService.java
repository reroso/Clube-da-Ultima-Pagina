package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.*;
import com.clubedolivro.clube_da_ultima_pagina.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class SeedService {
    private final UsuarioRepository usuarioRepository;
    private final GrupoRepository grupoRepository;
    private final LivroRepository livroRepository;
    private final EncontroRepository encontroRepository;
    private final GrupoLivroRepository grupoLivroRepository;
    private final UsuarioGrupoRepository usuarioGrupoRepository;

    public SeedService(
            UsuarioRepository usuarioRepository,
            GrupoRepository grupoRepository,
            LivroRepository livroRepository,
            EncontroRepository encontroRepository,
            GrupoLivroRepository grupoLivroRepository,
            UsuarioGrupoRepository usuarioGrupoRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.grupoRepository = grupoRepository;
        this.livroRepository = livroRepository;
        this.encontroRepository = encontroRepository;
        this.grupoLivroRepository = grupoLivroRepository;
        this.usuarioGrupoRepository = usuarioGrupoRepository;
    }

    @Transactional
    public void seedDatabase() {
        // Usuários
        Usuario usuario1 = new Usuario();
        usuario1.setNome("Alice");
        usuario1.setEmail("alice@email.com");
        usuario1.setSenha("123456");
        usuario1.setPerfil(PerfilEnum.ADMINISTRADOR);
        usuarioRepository.save(usuario1);

        Usuario usuario2 = new Usuario();
        usuario2.setNome("Bob");
        usuario2.setEmail("bob@email.com");
        usuario2.setSenha("123456");
        usuario2.setPerfil(PerfilEnum.MEMBRO);
        usuarioRepository.save(usuario2);

        // Livros
        Livro livro1 = new Livro();
        livro1.setTitulo("Dom Casmurro");
        livro1.setAutor("Machado de Assis");
        livro1.setDescricao("Clássico da literatura brasileira.");
        livroRepository.save(livro1);

        Livro livro2 = new Livro();
        livro2.setTitulo("1984");
        livro2.setAutor("George Orwell");
        livro2.setDescricao("Distopia política.");
        livroRepository.save(livro2);

        // Grupos
        Grupo grupo1 = new Grupo();
        grupo1.setNome("Clube Machado");
        grupo1.setDescricao("Grupo para fãs de Machado de Assis");
        grupo1.setLider(usuario1);
        grupoRepository.save(grupo1);

        Grupo grupo2 = new Grupo();
        grupo2.setNome("Clube Distopia");
        grupo2.setDescricao("Grupo para discutir distopias");
        grupo2.setLider(usuario2);
        grupoRepository.save(grupo2);

        // Encontros
        Encontro encontro1 = new Encontro();
        encontro1.setGrupo(grupo1);
        encontro1.setDataHora(LocalDateTime.now().plusDays(7));
        encontro1.setDescricao("Primeiro encontro do Clube Machado");
        encontroRepository.save(encontro1);

        Encontro encontro2 = new Encontro();
        encontro2.setGrupo(grupo2);
        encontro2.setDataHora(LocalDateTime.now().plusDays(14));
        encontro2.setDescricao("Primeiro encontro do Clube Distopia");
        encontroRepository.save(encontro2);

        // GrupoLivro
        GrupoLivro grupoLivro1 = new GrupoLivro();
        grupoLivro1.setGrupo(grupo1);
        grupoLivro1.setLivro(livro1);
        grupoLivro1.setObservacao("Livro do mês");
        grupoLivroRepository.save(grupoLivro1);

        GrupoLivro grupoLivro2 = new GrupoLivro();
        grupoLivro2.setGrupo(grupo2);
        grupoLivro2.setLivro(livro2);
        grupoLivro2.setObservacao("Livro do mês");
        grupoLivroRepository.save(grupoLivro2);

        // UsuarioGrupo
        UsuarioGrupo usuarioGrupo1 = new UsuarioGrupo();
        usuarioGrupo1.setUsuario(usuario1);
        usuarioGrupo1.setGrupo(grupo1);
        usuarioGrupoRepository.save(usuarioGrupo1);

        UsuarioGrupo usuarioGrupo2 = new UsuarioGrupo();
        usuarioGrupo2.setUsuario(usuario2);
        usuarioGrupo2.setGrupo(grupo2);
        usuarioGrupoRepository.save(usuarioGrupo2);
    }
}
