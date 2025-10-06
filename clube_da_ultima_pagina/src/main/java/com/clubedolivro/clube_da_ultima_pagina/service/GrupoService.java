package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.entity.PerfilEnum;
import com.clubedolivro.clube_da_ultima_pagina.exception.GrupoException;
import com.clubedolivro.clube_da_ultima_pagina.exception.ClubeLivroException;
import com.clubedolivro.clube_da_ultima_pagina.repository.GrupoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {
    
    private final GrupoRepository grupoRepository;
    private final UsuarioService usuarioService;

    public GrupoService(GrupoRepository grupoRepository, UsuarioService usuarioService) {
        this.grupoRepository = grupoRepository;
        this.usuarioService = usuarioService;
    }

    public Grupo salvar(Grupo grupo) {
        try {
            // Validações específicas de negócio
            if (grupo.getNome() == null || grupo.getNome().trim().isEmpty()) {
                throw new GrupoException("O nome do grupo não pode estar vazio.");
            }
            
            if (grupo.getLider() == null) {
                throw new GrupoException("O grupo deve ter um líder.");
            }
            
            // Verificar se já existe um grupo com o mesmo nome
            if (existePorNome(grupo.getNome())) {
                throw new GrupoException("Já existe um grupo cadastrado com o nome '" + grupo.getNome() + "'.");
            }
            
            // Salvar o grupo
            Grupo grupoSalvo = grupoRepository.save(grupo);
            
            // Promover o líder para LIDER_GRUPO se ainda não for
            Usuario lider = grupo.getLider();
            if (lider.getPerfil() == PerfilEnum.MEMBRO) {
                lider.setPerfil(PerfilEnum.LIDER_GRUPO);
                usuarioService.atualizar(lider.getId(), lider);
            }
            
            return grupoSalvo;
            
        } catch (GrupoException e) {
            // Re-lança exceções de negócio
            throw e;
        } catch (Exception e) {
            // Converte outras exceções em exceção geral da aplicação
            throw new ClubeLivroException("Erro interno ao salvar o grupo. Tente novamente.", e);
        }
    }

    public List<Grupo> listarTodos() {
        try {
            return grupoRepository.findAll();
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar a lista de grupos.", e);
        }
    }

    public Grupo buscarPorId(Integer id) {
        try {
            if (id == null) {
                throw new GrupoException("ID do grupo não pode ser nulo.");
            }
            
            Optional<Grupo> grupo = grupoRepository.findById(id);
            if (grupo.isEmpty()) {
                throw new GrupoException("Grupo com ID " + id + " não encontrado.");
            }
            
            return grupo.get();
            
        } catch (GrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar grupo por ID.", e);
        }
    }

    public Grupo atualizar(Integer id, Grupo grupoAtualizado) {
        try {
            if (id == null) {
                throw new GrupoException("ID do grupo não pode ser nulo para atualização.");
            }
            
            // Buscar o grupo existente
            Grupo grupoExistente = buscarPorId(id);
            
            // Validações específicas de negócio
            if (grupoAtualizado.getNome() == null || grupoAtualizado.getNome().trim().isEmpty()) {
                throw new GrupoException("O nome do grupo não pode estar vazio.");
            }
            
            if (grupoAtualizado.getLider() == null) {
                throw new GrupoException("O grupo deve ter um líder.");
            }
            
            // Verificar se já existe outro grupo com o mesmo nome (exceto o atual)
            Optional<Grupo> grupoComMesmoNome = buscarPorNome(grupoAtualizado.getNome());
            if (grupoComMesmoNome.isPresent() && !grupoComMesmoNome.get().getId().equals(id)) {
                throw new GrupoException("Já existe outro grupo cadastrado com o nome '" + grupoAtualizado.getNome() + "'.");
            }
            
            // Atualizar os campos
            grupoExistente.setNome(grupoAtualizado.getNome());
            grupoExistente.setDescricao(grupoAtualizado.getDescricao());
            grupoExistente.setLider(grupoAtualizado.getLider());
            
            return grupoRepository.save(grupoExistente);
            
        } catch (GrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao atualizar o grupo.", e);
        }
    }

    public void excluir(Integer id) {
        try {
            if (id == null) {
                throw new GrupoException("ID do grupo não pode ser nulo.");
            }
            
            if (!grupoRepository.existsById(id)) {
                throw new GrupoException("Grupo com ID " + id + " não encontrado para exclusão.");
            }
            
            grupoRepository.deleteById(id);
            
        } catch (GrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao excluir o grupo.", e);
        }
    }

    // Métodos adicionais usando as queries customizadas do repositório
    public Optional<Grupo> buscarPorNome(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new GrupoException("Nome não pode estar vazio na busca.");
            }
            return grupoRepository.findByNomeIgnoreCase(nome);
        } catch (GrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar grupo por nome.", e);
        }
    }
    
    public List<Grupo> buscarPorNomeContendo(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new GrupoException("Termo de busca não pode estar vazio.");
            }
            return grupoRepository.findByNomeContainingIgnoreCase(nome);
        } catch (GrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar grupos por nome.", e);
        }
    }
    
    public List<Grupo> buscarPorLider(Usuario lider) {
        try {
            if (lider == null) {
                throw new GrupoException("Líder não pode ser nulo na busca.");
            }
            return grupoRepository.findByLider(lider);
        } catch (GrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar grupos por líder.", e);
        }
    }
    
    public boolean existePorNome(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                return false;
            }
            return grupoRepository.existsByNomeIgnoreCase(nome);
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao verificar existência do grupo.", e);
        }
    }
}
