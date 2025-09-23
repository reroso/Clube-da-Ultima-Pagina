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
    private final PerfilRepository perfilRepository;

    public SeedService(
            UsuarioRepository usuarioRepository,
            GrupoRepository grupoRepository,
            LivroRepository livroRepository,
            EncontroRepository encontroRepository,
            GrupoLivroRepository grupoLivroRepository,
            UsuarioGrupoRepository usuarioGrupoRepository,
            PerfilRepository perfilRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.grupoRepository = grupoRepository;
        this.livroRepository = livroRepository;
        this.encontroRepository = encontroRepository;
        this.grupoLivroRepository = grupoLivroRepository;
        this.usuarioGrupoRepository = usuarioGrupoRepository;
        this.perfilRepository = perfilRepository;
    }

    @Transactional
    public void seedDatabase() {
        // Primeiro, garantir que os 3 perfis do enum existam (criar apenas se não existir)
        Perfil perfilAdmin = perfilRepository.findByNome("ADMINISTRADOR").orElseGet(() -> {
            Perfil p = new Perfil();
            p.setNome("ADMINISTRADOR");
            return perfilRepository.save(p);
        });
        
        @SuppressWarnings("unused")
        Perfil perfilLider = perfilRepository.findByNome("LIDER_GRUPO").orElseGet(() -> {
            Perfil p = new Perfil();
            p.setNome("LIDER_GRUPO");
            return perfilRepository.save(p);
        });
        
        Perfil perfilMembro = perfilRepository.findByNome("MEMBRO").orElseGet(() -> {
            Perfil p = new Perfil();
            p.setNome("MEMBRO");
            return perfilRepository.save(p);
        });

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
        usuarioGrupo1.setPerfil(perfilAdmin);
        usuarioGrupoRepository.save(usuarioGrupo1);

        UsuarioGrupo usuarioGrupo2 = new UsuarioGrupo();
        usuarioGrupo2.setUsuario(usuario2);
        usuarioGrupo2.setGrupo(grupo2);
        usuarioGrupo2.setPerfil(perfilMembro);
        usuarioGrupoRepository.save(usuarioGrupo2);
    }
}
