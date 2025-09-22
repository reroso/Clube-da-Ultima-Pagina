package com.clubedolivro.clube_da_ultima_pagina.repository;

import com.clubedolivro.clube_da_ultima_pagina.entity.GrupoLivro;
import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoLivroRepository extends JpaRepository<GrupoLivro, Integer> {
    
    // Buscar por grupo específico
    List<GrupoLivro> findByGrupo(Grupo grupo);
    
    // Buscar por ID do grupo
    List<GrupoLivro> findByGrupoId(Integer grupoId);
    
    // Buscar por livro específico
    List<GrupoLivro> findByLivro(Livro livro);
    
    // Buscar por ID do livro
    List<GrupoLivro> findByLivroId(Integer livroId);
    
    // Verificar se já existe associação entre grupo e livro
    boolean existsByGrupoIdAndLivroId(Integer grupoId, Integer livroId);
    
    // Buscar associação específica entre grupo e livro
    Optional<GrupoLivro> findByGrupoIdAndLivroId(Integer grupoId, Integer livroId);
    
    // Buscar por observação contendo texto
    List<GrupoLivro> findByObservacaoContainingIgnoreCase(String observacao);
    
    // Query customizada para buscar associações com informações completas
    @Query("SELECT gl FROM GrupoLivro gl " +
           "JOIN FETCH gl.grupo g " +
           "JOIN FETCH gl.livro l " +
           "JOIN FETCH g.lider " +
           "ORDER BY g.nome, l.titulo")
    List<GrupoLivro> findAllWithDetails();
    
    // Query para buscar por nome do grupo
    @Query("SELECT gl FROM GrupoLivro gl " +
           "JOIN gl.grupo g " +
           "WHERE LOWER(g.nome) LIKE LOWER(CONCAT('%', :nomeGrupo, '%'))")
    List<GrupoLivro> findByGrupoNomeContainingIgnoreCase(@Param("nomeGrupo") String nomeGrupo);
    
    // Query para buscar por título do livro
    @Query("SELECT gl FROM GrupoLivro gl " +
           "JOIN gl.livro l " +
           "WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :tituloLivro, '%'))")
    List<GrupoLivro> findByLivroTituloContainingIgnoreCase(@Param("tituloLivro") String tituloLivro);
    
    // Query para buscar por autor do livro
    @Query("SELECT gl FROM GrupoLivro gl " +
           "JOIN gl.livro l " +
           "WHERE LOWER(l.autor) LIKE LOWER(CONCAT('%', :autorLivro, '%'))")
    List<GrupoLivro> findByLivroAutorContainingIgnoreCase(@Param("autorLivro") String autorLivro);
}