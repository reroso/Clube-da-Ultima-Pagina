package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Encontro;
import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.exception.EncontroException;
import com.clubedolivro.clube_da_ultima_pagina.exception.ClubeLivroException;
import com.clubedolivro.clube_da_ultima_pagina.repository.EncontroRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EncontroService {
    
    private final EncontroRepository encontroRepository;

    public EncontroService(EncontroRepository encontroRepository) {
        this.encontroRepository = encontroRepository;
    }

    public Encontro salvar(Encontro encontro) {
        try {
            // Validações específicas de negócio
            if (encontro.getGrupo() == null) {
                throw new EncontroException("O grupo é obrigatório para o encontro.");
            }
            
            if (encontro.getDataHora() == null) {
                throw new EncontroException("A data e hora são obrigatórias para o encontro.");
            }
            
            // Validar se a data não é no passado (com tolerância de 1 hora)
            LocalDateTime agora = LocalDateTime.now().minusHours(1);
            if (encontro.getDataHora().isBefore(agora)) {
                throw new EncontroException("Não é possível agendar encontros no passado.");
            }
            
            // Verificar conflitos de horário (mesmo grupo em horário próximo - 2 horas antes/depois)
            LocalDateTime inicio = encontro.getDataHora().minusHours(2);
            LocalDateTime fim = encontro.getDataHora().plusHours(2);
            
            Long conflitos = encontroRepository.countConflitosHorario(
                encontro.getGrupo().getId(), 
                encontro.getId() != null ? encontro.getId() : -1, 
                inicio, 
                fim
            );
            
            if (conflitos > 0) {
                throw new EncontroException("Já existe um encontro agendado para este grupo em horário próximo (menos de 2 horas de diferença).");
            }
            
            return encontroRepository.save(encontro);
            
        } catch (EncontroException e) {
            // Re-lança exceções de negócio
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao salvar o encontro.", e);
        }
    }

    public List<Encontro> listarTodos() {
        try {
            return encontroRepository.findAllWithDetails();
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao listar todos os encontros.", e);
        }
    }

    public Encontro buscarPorId(Integer id) {
        try {
            if (id == null) {
                throw new EncontroException("ID do encontro não pode ser nulo.");
            }
            
            return encontroRepository.findById(id)
                    .orElseThrow(() -> new EncontroException("Encontro com ID " + id + " não encontrado."));
                    
        } catch (EncontroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao buscar o encontro.", e);
        }
    }

    public Encontro atualizar(Integer id, Encontro encontroAtualizado) {
        try {
            if (id == null) {
                throw new EncontroException("ID do encontro não pode ser nulo para atualização.");
            }
            
            // Buscar o encontro existente
            Encontro encontroExistente = buscarPorId(id);
            
            // Validações específicas de negócio
            if (encontroAtualizado.getGrupo() == null) {
                throw new EncontroException("O grupo é obrigatório para o encontro.");
            }
            
            if (encontroAtualizado.getDataHora() == null) {
                throw new EncontroException("A data e hora são obrigatórias para o encontro.");
            }
            
            // Validar se a nova data não é no passado (com tolerância de 1 hora)
            LocalDateTime agora = LocalDateTime.now().minusHours(1);
            if (encontroAtualizado.getDataHora().isBefore(agora)) {
                throw new EncontroException("Não é possível agendar encontros no passado.");
            }
            
            // Verificar conflitos de horário
            LocalDateTime inicio = encontroAtualizado.getDataHora().minusHours(2);
            LocalDateTime fim = encontroAtualizado.getDataHora().plusHours(2);
            
            Long conflitos = encontroRepository.countConflitosHorario(
                encontroAtualizado.getGrupo().getId(), 
                id, 
                inicio, 
                fim
            );
            
            if (conflitos > 0) {
                throw new EncontroException("Já existe um encontro agendado para este grupo em horário próximo (menos de 2 horas de diferença).");
            }
            
            // Atualizar os campos
            encontroExistente.setGrupo(encontroAtualizado.getGrupo());
            encontroExistente.setDataHora(encontroAtualizado.getDataHora());
            encontroExistente.setDescricao(encontroAtualizado.getDescricao());
            
            return encontroRepository.save(encontroExistente);
            
        } catch (EncontroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao atualizar o encontro.", e);
        }
    }

    public void excluir(Integer id) {
        try {
            if (id == null) {
                throw new EncontroException("ID do encontro não pode ser nulo.");
            }
            
            if (!encontroRepository.existsById(id)) {
                throw new EncontroException("Encontro com ID " + id + " não encontrado para exclusão.");
            }
            
            encontroRepository.deleteById(id);
            
        } catch (EncontroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao excluir o encontro.", e);
        }
    }

    // Métodos adicionais usando as queries customizadas do repositório
    public List<Encontro> buscarPorGrupo(Grupo grupo) {
        return encontroRepository.findByGrupo(grupo);
    }
    
    public List<Encontro> buscarPorGrupoId(Integer grupoId) {
        return encontroRepository.findByGrupoId(grupoId);
    }

    public List<Encontro> buscarEncontrosFuturos() {
        return encontroRepository.findEncontrosFuturos(LocalDateTime.now());
    }
    
    public List<Encontro> buscarEncontrosPassados() {
        return encontroRepository.findEncontrosPassados(LocalDateTime.now());
    }
    
    public List<Encontro> buscarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return encontroRepository.findByDataHoraBetween(inicio, fim);
    }
    
    public List<Encontro> buscarPorNomeGrupo(String nomeGrupo) {
        return encontroRepository.findByGrupoNomeContainingIgnoreCase(nomeGrupo);
    }
    
    public List<Encontro> buscarPorDescricao(String descricao) {
        return encontroRepository.findByDescricaoContainingIgnoreCase(descricao);
    }
    
    public List<Encontro> buscarPorGrupoEPeriodo(Integer grupoId, LocalDateTime inicio, LocalDateTime fim) {
        return encontroRepository.findByGrupoIdAndDataHoraBetween(grupoId, inicio, fim);
    }
}
