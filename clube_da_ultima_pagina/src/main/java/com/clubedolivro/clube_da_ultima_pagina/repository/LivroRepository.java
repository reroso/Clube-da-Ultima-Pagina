package com.clubedolivro.clube_da_ultima_pagina.repository;

import com.clubedolivro.clube_da_ultima_pagina.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {
    
    // Buscar livro por título (ignorando maiúsculas/minúsculas)
    Optional<Livro> findByTituloIgnoreCase(String titulo);
    
    // Buscar livros por autor (ignorando maiúsculas/minúsculas)
    List<Livro> findByAutorContainingIgnoreCase(String autor);
    
    // Buscar livros por título contendo uma palavra
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    
    // Verificar se existe livro com título específico
    boolean existsByTituloIgnoreCase(String titulo);
}
