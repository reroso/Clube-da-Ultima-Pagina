package com.clubedolivro.clube_da_ultima_pagina.repository;

import com.clubedolivro.clube_da_ultima_pagina.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    
    /**
     * Busca perfil por nome
     */
    Optional<Perfil> findByNome(String nome);
    
    /**
     * Verifica se existe perfil com o nome especificado
     */
    boolean existsByNome(String nome);
}