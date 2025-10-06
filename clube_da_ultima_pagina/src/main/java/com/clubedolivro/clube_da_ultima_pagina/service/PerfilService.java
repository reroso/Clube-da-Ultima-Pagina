package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Perfil;
import com.clubedolivro.clube_da_ultima_pagina.repository.PerfilRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
@Transactional
public class PerfilService {
    
    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }
    
    @PostConstruct
    public void inicializarPerfis() {
        // Inicializa os perfis básicos se ainda não existirem no banco
        criarPerfilSeNaoExiste("Membro");
        criarPerfilSeNaoExiste("Líder");
        criarPerfilSeNaoExiste("Administrador");
    }

    private void criarPerfilSeNaoExiste(String nome) {
        if (!perfilRepository.existsByNome(nome)) {
            Perfil perfil = new Perfil();
            perfil.setNome(nome);
            perfilRepository.save(perfil);
        }
    }

    public List<Perfil> listarTodos() {
        return perfilRepository.findAll();
    }

    public Perfil buscarPorNome(String nome) {
        return perfilRepository.findByNome(nome).orElse(null);
    }

    public Perfil salvar(Perfil perfil) {
        return perfilRepository.save(perfil);
    }
    
    public Perfil buscarPorId(Integer id) {
        return perfilRepository.findById(id).orElse(null);
    }
    
    public Perfil atualizar(Integer id, Perfil perfilAtualizado) {
        Perfil perfilExistente = perfilRepository.findById(id).orElse(null);
        if (perfilExistente == null) {
            return null;
        }
        
        perfilExistente.setNome(perfilAtualizado.getNome());
        return perfilRepository.save(perfilExistente);
    }
    
    public void excluir(Integer id) {
        if (perfilRepository.existsById(id)) {
            perfilRepository.deleteById(id);
        }
    }
}
