package com.clubedolivro.clube_da_ultima_pagina.repository;

import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    // Buscar usuário por email (ignorando maiúsculas/minúsculas)
    Optional<Usuario> findByEmailIgnoreCase(String email);
    
    // Buscar usuários por nome (ignorando maiúsculas/minúsculas)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
    
    // Verificar se existe usuário com email específico
    boolean existsByEmailIgnoreCase(String email);
    
    // Buscar usuários por perfil
    List<Usuario> findByPerfil(com.clubedolivro.clube_da_ultima_pagina.entity.PerfilEnum perfil);
}