package com.clubedolivro.clube_da_ultima_pagina.repository;

import com.clubedolivro.clube_da_ultima_pagina.entity.Encontro;
import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EncontroRepository extends JpaRepository<Encontro, Integer> {
    
    // Buscar por grupo específico
    List<Encontro> findByGrupo(Grupo grupo);
    
    // Buscar por ID do grupo
    List<Encontro> findByGrupoId(Integer grupoId);
    
    // Buscar encontros por período (data/hora)
    List<Encontro> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    
    // Buscar encontros a partir de uma data
    List<Encontro> findByDataHoraAfter(LocalDateTime dataHora);
    
    // Buscar encontros antes de uma data
    List<Encontro> findByDataHoraBefore(LocalDateTime dataHora);
    
    // Buscar por descrição contendo texto
    List<Encontro> findByDescricaoContainingIgnoreCase(String descricao);
    
    // Query customizada para buscar encontros com informações completas
    @Query("SELECT e FROM Encontro e " +
           "JOIN FETCH e.grupo g " +
           "JOIN FETCH g.lider " +
           "ORDER BY e.dataHora DESC")
    List<Encontro> findAllWithDetails();
    
    // Query para buscar encontros futuros
    @Query("SELECT e FROM Encontro e " +
           "WHERE e.dataHora > :agora " +
           "ORDER BY e.dataHora ASC")
    List<Encontro> findEncontrosFuturos(@Param("agora") LocalDateTime agora);
    
    // Query para buscar encontros passados
    @Query("SELECT e FROM Encontro e " +
           "WHERE e.dataHora < :agora " +
           "ORDER BY e.dataHora DESC")
    List<Encontro> findEncontrosPassados(@Param("agora") LocalDateTime agora);
    
    // Query para buscar por nome do grupo
    @Query("SELECT e FROM Encontro e " +
           "JOIN e.grupo g " +
           "WHERE LOWER(g.nome) LIKE LOWER(CONCAT('%', :nomeGrupo, '%')) " +
           "ORDER BY e.dataHora DESC")
    List<Encontro> findByGrupoNomeContainingIgnoreCase(@Param("nomeGrupo") String nomeGrupo);
    
    // Query para buscar encontros de um grupo em um período específico
    @Query("SELECT e FROM Encontro e " +
           "WHERE e.grupo.id = :grupoId " +
           "AND e.dataHora BETWEEN :inicio AND :fim " +
           "ORDER BY e.dataHora ASC")
    List<Encontro> findByGrupoIdAndDataHoraBetween(@Param("grupoId") Integer grupoId, 
                                                   @Param("inicio") LocalDateTime inicio, 
                                                   @Param("fim") LocalDateTime fim);
    
    // Query para verificar conflitos de horário (mesmo grupo em horário próximo)
    @Query("SELECT COUNT(e) FROM Encontro e " +
           "WHERE e.grupo.id = :grupoId " +
           "AND e.id <> :encontroId " +
           "AND e.dataHora BETWEEN :inicio AND :fim")
    Long countConflitosHorario(@Param("grupoId") Integer grupoId, 
                               @Param("encontroId") Integer encontroId,
                               @Param("inicio") LocalDateTime inicio, 
                               @Param("fim") LocalDateTime fim);
}