package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.dto.GrupoLivroDTO;
import com.clubedolivro.clube_da_ultima_pagina.entity.GrupoLivro;
import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Livro;
import com.clubedolivro.clube_da_ultima_pagina.exception.GrupoLivroException;
import com.clubedolivro.clube_da_ultima_pagina.exception.ClubeLivroException;
import com.clubedolivro.clube_da_ultima_pagina.repository.GrupoLivroRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GrupoLivroService {
    
    private final GrupoLivroRepository grupoLivroRepository;

    public GrupoLivroService(GrupoLivroRepository grupoLivroRepository) {
        this.grupoLivroRepository = grupoLivroRepository;
    }

    public GrupoLivro salvar(GrupoLivro grupoLivro) {
        try {
            // Validações específicas de negócio
            if (grupoLivro.getGrupo() == null) {
                throw new GrupoLivroException("O grupo não pode ser nulo.");
            }
            
            if (grupoLivro.getLivro() == null) {
                throw new GrupoLivroException("O livro não pode ser nulo.");
            }
            
            // Verificar se já existe uma associação entre este grupo e livro
            if (existeAssociacao(grupoLivro.getGrupo().getId(), grupoLivro.getLivro().getId())) {
                throw new GrupoLivroException("Esta associação entre grupo e livro já existe.");
            }
            
            return grupoLivroRepository.save(grupoLivro);
            
        } catch (GrupoLivroException e) {
            // Re-lança exceções de negócio
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao salvar a associação grupo-livro.", e);
        }
    }

    public List<GrupoLivro> listarTodos() {
        try {
            return grupoLivroRepository.findAllWithDetails();
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao listar todas as associações grupo-livro.", e);
        }
    }

    public GrupoLivro buscarPorId(Integer id) {
        try {
            if (id == null) {
                throw new GrupoLivroException("ID da associação não pode ser nulo.");
            }
            
            return grupoLivroRepository.findById(id)
                    .orElseThrow(() -> new GrupoLivroException("Associação grupo-livro com ID " + id + " não encontrada."));
                    
        } catch (GrupoLivroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao buscar a associação grupo-livro.", e);
        }
    }

    public GrupoLivro atualizar(Integer id, GrupoLivro grupoLivroAtualizado) {
        try {
            if (id == null) {
                throw new GrupoLivroException("ID da associação não pode ser nulo para atualização.");
            }
            
            // Buscar a associação existente
            GrupoLivro grupoLivroExistente = buscarPorId(id);
            
            // Validações específicas de negócio
            if (grupoLivroAtualizado.getGrupo() == null) {
                throw new GrupoLivroException("O grupo não pode ser nulo.");
            }
            
            if (grupoLivroAtualizado.getLivro() == null) {
                throw new GrupoLivroException("O livro não pode ser nulo.");
            }
            
            // Verificar se já existe outra associação com os novos grupo e livro (exceto a atual)
            Optional<GrupoLivro> associacaoExistente = grupoLivroRepository.findByGrupoIdAndLivroId(
                grupoLivroAtualizado.getGrupo().getId(), 
                grupoLivroAtualizado.getLivro().getId()
            );
            
            if (associacaoExistente.isPresent() && !associacaoExistente.get().getId().equals(id)) {
                throw new GrupoLivroException("Já existe outra associação entre este grupo e livro.");
            }
            
            // Atualizar os campos
            grupoLivroExistente.setGrupo(grupoLivroAtualizado.getGrupo());
            grupoLivroExistente.setLivro(grupoLivroAtualizado.getLivro());
            grupoLivroExistente.setObservacao(grupoLivroAtualizado.getObservacao());
            
            return grupoLivroRepository.save(grupoLivroExistente);
            
        } catch (GrupoLivroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao atualizar a associação grupo-livro.", e);
        }
    }

    public void excluir(Integer id) {
        try {
            if (id == null) {
                throw new GrupoLivroException("ID da associação não pode ser nulo.");
            }
            
            if (!grupoLivroRepository.existsById(id)) {
                throw new GrupoLivroException("Associação grupo-livro com ID " + id + " não encontrada para exclusão.");
            }
            
            grupoLivroRepository.deleteById(id);
            
        } catch (GrupoLivroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao excluir a associação grupo-livro.", e);
        }
    }

    // Métodos adicionais usando as queries customizadas do repositório
    public List<GrupoLivro> buscarPorGrupo(Grupo grupo) {
        return grupoLivroRepository.findByGrupo(grupo);
    }
    
    public List<GrupoLivro> buscarPorGrupoId(Integer grupoId) {
        return grupoLivroRepository.findByGrupoId(grupoId);
    }

    public List<GrupoLivro> buscarPorLivro(Livro livro) {
        return grupoLivroRepository.findByLivro(livro);
    }
    
    public List<GrupoLivro> buscarPorLivroId(Integer livroId) {
        return grupoLivroRepository.findByLivroId(livroId);
    }

    public boolean existeAssociacao(Integer grupoId, Integer livroId) {
        return grupoLivroRepository.existsByGrupoIdAndLivroId(grupoId, livroId);
    }
    
    public List<GrupoLivro> buscarPorNomeGrupo(String nomeGrupo) {
        return grupoLivroRepository.findByGrupoNomeContainingIgnoreCase(nomeGrupo);
    }
    
    public List<GrupoLivro> buscarPorTituloLivro(String tituloLivro) {
        return grupoLivroRepository.findByLivroTituloContainingIgnoreCase(tituloLivro);
    }
    
    public List<GrupoLivro> buscarPorAutorLivro(String autorLivro) {
        return grupoLivroRepository.findByLivroAutorContainingIgnoreCase(autorLivro);
    }
    
    public List<GrupoLivro> buscarPorObservacao(String observacao) {
        return grupoLivroRepository.findByObservacaoContainingIgnoreCase(observacao);
    }

    // Métodos para trabalhar com DTO
    public List<GrupoLivroDTO> listarTodosDTO() {
        return listarTodos().stream()
                .map(this::converterParaDTO)
                .toList();
    }
    
    public GrupoLivroDTO buscarPorIdDTO(Integer id) {
        GrupoLivro grupoLivro = buscarPorId(id);
        return converterParaDTO(grupoLivro);
    }
    
    public List<GrupoLivroDTO> buscarPorGrupoDTO(Grupo grupo) {
        return buscarPorGrupo(grupo).stream()
                .map(this::converterParaDTO)
                .toList();
    }
    
    private GrupoLivroDTO converterParaDTO(GrupoLivro grupoLivro) {
        return new GrupoLivroDTO(
            grupoLivro.getId(),
            grupoLivro.getObservacao(),
            grupoLivro.getGrupo().getId(),
            grupoLivro.getGrupo().getNome(),
            grupoLivro.getGrupo().getDescricao(),
            grupoLivro.getGrupo().getLider() != null ? grupoLivro.getGrupo().getLider().getNome() : "Sem líder",
            grupoLivro.getLivro().getId(),
            grupoLivro.getLivro().getTitulo(),
            grupoLivro.getLivro().getAutor(),
            grupoLivro.getLivro().getDescricao()
        );
    }
}
