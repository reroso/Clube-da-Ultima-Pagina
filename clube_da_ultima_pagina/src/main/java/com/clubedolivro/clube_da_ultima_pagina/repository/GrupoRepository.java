package com.clubedolivro.clube_da_ultima_pagina.repository;

import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
    
    // Buscar grupo por nome (ignorando maiúsculas/minúsculas)
    Optional<Grupo> findByNomeIgnoreCase(String nome);
    
    // Buscar grupos por nome contendo uma palavra
    List<Grupo> findByNomeContainingIgnoreCase(String nome);
    
    // Verificar se existe grupo com nome específico
    boolean existsByNomeIgnoreCase(String nome);
    
    // Buscar grupos por líder
    List<Grupo> findByLider(Usuario lider);
    
    // Buscar grupos onde o usuário é o líder
    List<Grupo> findByLiderId(Integer liderId);
}