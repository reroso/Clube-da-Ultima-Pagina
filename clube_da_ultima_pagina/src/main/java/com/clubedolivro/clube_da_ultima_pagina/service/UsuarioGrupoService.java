package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.UsuarioGrupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Perfil;
import com.clubedolivro.clube_da_ultima_pagina.exception.UsuarioGrupoException;
import com.clubedolivro.clube_da_ultima_pagina.exception.ClubeLivroException;
import com.clubedolivro.clube_da_ultima_pagina.repository.UsuarioGrupoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioGrupoService {

    private final UsuarioGrupoRepository usuarioGrupoRepository;
    private final PerfilService perfilService;

    public UsuarioGrupoService(UsuarioGrupoRepository usuarioGrupoRepository,
                            PerfilService perfilService) {
        this.usuarioGrupoRepository = usuarioGrupoRepository;
        this.perfilService = perfilService;
    }

    private Perfil buscarPerfilMembro() {
        Perfil perfil = perfilService.buscarPorNome("Membro");
        if (perfil == null) {
            // Tentar criar o perfil se não existir
            try {
                Perfil novoPerfil = new Perfil();
                novoPerfil.setNome("Membro");
                perfil = perfilService.salvar(novoPerfil);
            } catch (Exception e) {
                throw new UsuarioGrupoException("Erro ao criar perfil 'Membro': " + e.getMessage());
            }
        }
        return perfil;
    }

    public UsuarioGrupo entrarNoGrupo(Usuario usuario, Grupo grupo) {
        try {
            // Validações básicas
            if (usuario == null) {
                throw new UsuarioGrupoException("Usuário é obrigatório para entrar no grupo.");
            }
            
            if (grupo == null) {
                throw new UsuarioGrupoException("Grupo é obrigatório para a associação.");
            }

            // Verificar se o usuário já está no grupo
            if (usuarioGrupoRepository.existsByUsuarioIdAndGrupoId(usuario.getId(), grupo.getId())) {
                throw new UsuarioGrupoException("O usuário já é membro deste grupo.");
            }

            // Buscar automaticamente o perfil "Membro"
            Perfil perfilMembro = buscarPerfilMembro();

            // Criar nova associação
            UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
            usuarioGrupo.setUsuario(usuario);
            usuarioGrupo.setGrupo(grupo);
            usuarioGrupo.setPerfil(perfilMembro);

            return usuarioGrupoRepository.save(usuarioGrupo);

        } catch (UsuarioGrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao adicionar usuário ao grupo.", e);
        }
    }

    public UsuarioGrupo salvar(UsuarioGrupo usuarioGrupo) {
        try {
            // Validações específicas de negócio
            if (usuarioGrupo.getUsuario() == null) {
                throw new UsuarioGrupoException("Usuário é obrigatório para a associação.");
            }
            
            if (usuarioGrupo.getGrupo() == null) {
                throw new UsuarioGrupoException("Grupo é obrigatório para a associação.");
            }
            
            // Se perfil não foi especificado, usar "Membro" como padrão
            if (usuarioGrupo.getPerfil() == null) {
                usuarioGrupo.setPerfil(buscarPerfilMembro());
            }

            // Se for uma nova associação, verificar duplicatas
            if (usuarioGrupo.getId() == null) {
                if (usuarioGrupoRepository.existsByUsuarioIdAndGrupoId(
                        usuarioGrupo.getUsuario().getId(), 
                        usuarioGrupo.getGrupo().getId())) {
                    throw new UsuarioGrupoException("O usuário já é membro deste grupo.");
                }
            }

            return usuarioGrupoRepository.save(usuarioGrupo);

        } catch (UsuarioGrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao salvar a associação usuário-grupo.", e);
        }
    }

    public List<UsuarioGrupo> listarTodos() {
        try {
            return usuarioGrupoRepository.findAllWithDetails();
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao listar todas as associações usuário-grupo.", e);
        }
    }

    public UsuarioGrupo buscarPorId(Integer id) {
        try {
            if (id == null) {
                throw new UsuarioGrupoException("ID da associação não pode ser nulo.");
            }

            return usuarioGrupoRepository.findById(id)
                    .orElseThrow(() -> new UsuarioGrupoException("Associação usuário-grupo com ID " + id + " não encontrada."));

        } catch (UsuarioGrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao buscar a associação usuário-grupo.", e);
        }
    }

    public UsuarioGrupo atualizar(Integer id, UsuarioGrupo usuarioGrupoAtualizado) {
        try {
            if (id == null) {
                throw new UsuarioGrupoException("ID da associação não pode ser nulo para atualização.");
            }

            // Buscar a associação existente
            UsuarioGrupo usuarioGrupoExistente = buscarPorId(id);

            // Validações específicas de negócio
            if (usuarioGrupoAtualizado.getUsuario() == null) {
                throw new UsuarioGrupoException("Usuário é obrigatório para a associação.");
            }
            
            if (usuarioGrupoAtualizado.getGrupo() == null) {
                throw new UsuarioGrupoException("Grupo é obrigatório para a associação.");
            }
            
            // Se perfil não foi especificado, usar "Membro" como padrão
            if (usuarioGrupoAtualizado.getPerfil() == null) {
                usuarioGrupoAtualizado.setPerfil(buscarPerfilMembro());
            }

            // Verificar se a nova combinação usuário-grupo já existe (exceto para o próprio registro)
            var associacaoExistente = usuarioGrupoRepository.findByUsuarioIdAndGrupoId(
                    usuarioGrupoAtualizado.getUsuario().getId(),
                    usuarioGrupoAtualizado.getGrupo().getId());

            if (associacaoExistente.isPresent() && !associacaoExistente.get().getId().equals(id)) {
                throw new UsuarioGrupoException("Já existe uma associação entre este usuário e grupo.");
            }

            // Atualizar os campos
            usuarioGrupoExistente.setUsuario(usuarioGrupoAtualizado.getUsuario());
            usuarioGrupoExistente.setGrupo(usuarioGrupoAtualizado.getGrupo());
            usuarioGrupoExistente.setPerfil(usuarioGrupoAtualizado.getPerfil());

            return usuarioGrupoRepository.save(usuarioGrupoExistente);

        } catch (UsuarioGrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao atualizar a associação usuário-grupo.", e);
        }
    }

    public void sairDoGrupo(Integer id) {
        try {
            if (id == null) {
                throw new UsuarioGrupoException("ID da associação não pode ser nulo.");
            }

            if (!usuarioGrupoRepository.existsById(id)) {
                throw new UsuarioGrupoException("Associação usuário-grupo com ID " + id + " não encontrada para remoção.");
            }

            usuarioGrupoRepository.deleteById(id);

        } catch (UsuarioGrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao remover usuário do grupo.", e);
        }
    }

    public void sairDoGrupo(Integer usuarioId, Integer grupoId) {
        try {
            if (usuarioId == null || grupoId == null) {
                throw new UsuarioGrupoException("ID do usuário e do grupo não podem ser nulos.");
            }

            var associacao = usuarioGrupoRepository.findByUsuarioIdAndGrupoId(usuarioId, grupoId);
            if (associacao.isEmpty()) {
                throw new UsuarioGrupoException("Usuário não é membro deste grupo.");
            }

            usuarioGrupoRepository.delete(associacao.get());

        } catch (UsuarioGrupoException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao remover usuário do grupo.", e);
        }
    }

    // Métodos de consulta usando as queries customizadas do repositório
    public List<UsuarioGrupo> buscarAssociacoesPorUsuario(Integer usuarioId) {
        return usuarioGrupoRepository.findByUsuarioIdWithDetails(usuarioId);
    }

    public List<UsuarioGrupo> buscarAssociacoesPorGrupo(Integer grupoId) {
        return usuarioGrupoRepository.findByGrupoIdWithDetails(grupoId);
    }

    public boolean existeAssociacao(Integer usuarioId, Integer grupoId) {
        return usuarioGrupoRepository.existsByUsuarioIdAndGrupoId(usuarioId, grupoId);
    }

    public UsuarioGrupo buscarAssociacao(Integer usuarioId, Integer grupoId) {
        return usuarioGrupoRepository.findByUsuarioIdAndGrupoId(usuarioId, grupoId).orElse(null);
    }

    public List<UsuarioGrupo> buscarPorPerfil(String perfilNome) {
        return usuarioGrupoRepository.findByPerfilNome(perfilNome);
    }

    public List<UsuarioGrupo> buscarLideres() {
        return usuarioGrupoRepository.findLideres();
    }

    public List<UsuarioGrupo> buscarMembros() {
        return usuarioGrupoRepository.findMembros();
    }

    public List<UsuarioGrupo> buscarPorNomeUsuario(String nomeUsuario) {
        return usuarioGrupoRepository.findByUsuarioNomeContainingIgnoreCase(nomeUsuario);
    }

    public List<UsuarioGrupo> buscarPorNomeGrupo(String nomeGrupo) {
        return usuarioGrupoRepository.findByGrupoNomeContainingIgnoreCase(nomeGrupo);
    }

    public Long contarMembrosPorGrupo(Integer grupoId) {
        return usuarioGrupoRepository.countMembersByGrupoId(grupoId);
    }

    public Long contarGruposPorUsuario(Integer usuarioId) {
        return usuarioGrupoRepository.countGruposByUsuarioId(usuarioId);
    }




}
