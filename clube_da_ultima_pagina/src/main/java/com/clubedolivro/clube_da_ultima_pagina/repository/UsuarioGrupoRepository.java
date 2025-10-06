package com.clubedolivro.clube_da_ultima_pagina.repository;

import com.clubedolivro.clube_da_ultima_pagina.entity.UsuarioGrupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, Integer> {

    /**
     * Busca associações por usuário
     */
    List<UsuarioGrupo> findByUsuario(Usuario usuario);
    
    /**
     * Busca associações por ID do usuário
     */
    List<UsuarioGrupo> findByUsuarioId(Integer usuarioId);

    /**
     * Busca associações por grupo
     */
    List<UsuarioGrupo> findByGrupo(Grupo grupo);
    
    /**
     * Busca associações por ID do grupo
     */
    List<UsuarioGrupo> findByGrupoId(Integer grupoId);

    /**
     * Busca associações por perfil
     */
    List<UsuarioGrupo> findByPerfil(Perfil perfil);
    
    /**
     * Busca associações por nome do perfil
     */
    List<UsuarioGrupo> findByPerfilNome(String perfilNome);

    /**
     * Verifica se existe associação entre usuário e grupo
     */
    boolean existsByUsuarioIdAndGrupoId(Integer usuarioId, Integer grupoId);

    /**
     * Busca associação específica entre usuário e grupo
     */
    Optional<UsuarioGrupo> findByUsuarioIdAndGrupoId(Integer usuarioId, Integer grupoId);

    /**
     * Busca associação específica entre usuário, grupo e perfil
     */
    Optional<UsuarioGrupo> findByUsuarioIdAndGrupoIdAndPerfilId(Integer usuarioId, Integer grupoId, Integer perfilId);

    /**
     * Busca todas as associações com detalhes (usando JOIN FETCH para evitar N+1)
     */
    @Query("SELECT ug FROM UsuarioGrupo ug " +
           "JOIN FETCH ug.usuario u " +
           "JOIN FETCH ug.grupo g " +
           "LEFT JOIN FETCH g.lider " +
           "JOIN FETCH ug.perfil p " +
           "ORDER BY g.nome, u.nome")
    List<UsuarioGrupo> findAllWithDetails();

    /**
     * Busca associações de um usuário com detalhes
     */
    @Query("SELECT ug FROM UsuarioGrupo ug " +
           "JOIN FETCH ug.usuario u " +
           "JOIN FETCH ug.grupo g " +
           "LEFT JOIN FETCH g.lider " +
           "JOIN FETCH ug.perfil p " +
           "WHERE u.id = :usuarioId " +
           "ORDER BY g.nome")
    List<UsuarioGrupo> findByUsuarioIdWithDetails(@Param("usuarioId") Integer usuarioId);

    /**
     * Busca associações de um grupo com detalhes
     */
    @Query("SELECT ug FROM UsuarioGrupo ug " +
           "JOIN FETCH ug.usuario u " +
           "JOIN FETCH ug.grupo g " +
           "LEFT JOIN FETCH g.lider " +
           "JOIN FETCH ug.perfil p " +
           "WHERE g.id = :grupoId " +
           "ORDER BY u.nome")
    List<UsuarioGrupo> findByGrupoIdWithDetails(@Param("grupoId") Integer grupoId);

    /**
     * Conta quantos membros um grupo tem
     */
    @Query("SELECT COUNT(ug) FROM UsuarioGrupo ug WHERE ug.grupo.id = :grupoId")
    Long countMembersByGrupoId(@Param("grupoId") Integer grupoId);

    /**
     * Conta quantos grupos um usuário participa
     */
    @Query("SELECT COUNT(ug) FROM UsuarioGrupo ug WHERE ug.usuario.id = :usuarioId")
    Long countGruposByUsuarioId(@Param("usuarioId") Integer usuarioId);

    /**
     * Busca líderes de grupos
     */
    @Query("SELECT ug FROM UsuarioGrupo ug " +
           "JOIN FETCH ug.usuario u " +
           "JOIN FETCH ug.grupo g " +
           "JOIN FETCH ug.perfil p " +
           "WHERE p.nome = 'Líder' " +
           "ORDER BY g.nome")
    List<UsuarioGrupo> findLideres();

    /**
     * Busca membros de grupos (não líderes)
     */
    @Query("SELECT ug FROM UsuarioGrupo ug " +
           "JOIN FETCH ug.usuario u " +
           "JOIN FETCH ug.grupo g " +
           "JOIN FETCH ug.perfil p " +
           "WHERE p.nome = 'Membro' " +
           "ORDER BY g.nome, u.nome")
    List<UsuarioGrupo> findMembros();

    /**
     * Busca por nome do usuário (case insensitive)
     */
    @Query("SELECT ug FROM UsuarioGrupo ug " +
           "JOIN FETCH ug.usuario u " +
           "JOIN FETCH ug.grupo g " +
           "JOIN FETCH ug.perfil p " +
           "WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nomeUsuario, '%')) " +
           "ORDER BY u.nome")
    List<UsuarioGrupo> findByUsuarioNomeContainingIgnoreCase(@Param("nomeUsuario") String nomeUsuario);

    /**
     * Busca por nome do grupo (case insensitive)
     */
    @Query("SELECT ug FROM UsuarioGrupo ug " +
           "JOIN FETCH ug.usuario u " +
           "JOIN FETCH ug.grupo g " +
           "JOIN FETCH ug.perfil p " +
           "WHERE LOWER(g.nome) LIKE LOWER(CONCAT('%', :nomeGrupo, '%')) " +
           "ORDER BY g.nome")
    List<UsuarioGrupo> findByGrupoNomeContainingIgnoreCase(@Param("nomeGrupo") String nomeGrupo);
}