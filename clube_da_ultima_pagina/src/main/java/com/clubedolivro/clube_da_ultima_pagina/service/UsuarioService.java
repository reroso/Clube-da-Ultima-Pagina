package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.entity.PerfilEnum;
import com.clubedolivro.clube_da_ultima_pagina.exception.UsuarioException;
import com.clubedolivro.clube_da_ultima_pagina.exception.ClubeLivroException;
import com.clubedolivro.clube_da_ultima_pagina.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvar(Usuario usuario) {
        try {
            // Validações específicas de negócio
            if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
                throw new UsuarioException("O nome do usuário não pode estar vazio.");
            }
            
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                throw new UsuarioException("O email do usuário não pode estar vazio.");
            }
            
            if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
                throw new UsuarioException("A senha do usuário não pode estar vazia.");
            }
            
            if (usuario.getPerfil() == null) {
                throw new UsuarioException("O perfil do usuário deve ser informado.");
            }
            
            // Verificar se já existe um usuário com o mesmo email
            if (existePorEmail(usuario.getEmail())) {
                throw new UsuarioException("Já existe um usuário cadastrado com o email '" + usuario.getEmail() + "'.");
            }
            
            return usuarioRepository.save(usuario);
            
        } catch (UsuarioException e) {
            // Re-lança exceções de negócio
            throw e;
        } catch (Exception e) {
            // Converte outras exceções em exceção geral da aplicação
            throw new ClubeLivroException("Erro interno ao salvar o usuário. Tente novamente.", e);
        }
    }

    public List<Usuario> listarTodos() {
        try {
            return usuarioRepository.findAll();
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar a lista de usuários.", e);
        }
    }

    public Usuario buscarPorId(Integer id) {
        try {
            if (id == null) {
                throw new UsuarioException("ID do usuário não pode ser nulo.");
            }
            
            Optional<Usuario> usuario = usuarioRepository.findById(id);
            if (usuario.isEmpty()) {
                throw new UsuarioException("Usuário com ID " + id + " não encontrado.");
            }
            
            return usuario.get();
            
        } catch (UsuarioException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar usuário por ID.", e);
        }
    }

    public Usuario atualizar(Integer id, Usuario usuarioAtualizado) {
        try {
            if (id == null) {
                throw new UsuarioException("ID do usuário não pode ser nulo para atualização.");
            }
            
            // Buscar o usuário existente
            Usuario usuarioExistente = buscarPorId(id);
            
            // Validações específicas de negócio
            if (usuarioAtualizado.getNome() == null || usuarioAtualizado.getNome().trim().isEmpty()) {
                throw new UsuarioException("O nome do usuário não pode estar vazio.");
            }
            
            if (usuarioAtualizado.getEmail() == null || usuarioAtualizado.getEmail().trim().isEmpty()) {
                throw new UsuarioException("O email do usuário não pode estar vazio.");
            }
            
            if (usuarioAtualizado.getSenha() == null || usuarioAtualizado.getSenha().trim().isEmpty()) {
                throw new UsuarioException("A senha do usuário não pode estar vazia.");
            }
            
            if (usuarioAtualizado.getPerfil() == null) {
                throw new UsuarioException("O perfil do usuário deve ser informado.");
            }
            
            // Verificar se já existe outro usuário com o mesmo email (exceto o atual)
            Optional<Usuario> usuarioComMesmoEmail = buscarPorEmail(usuarioAtualizado.getEmail());
            if (usuarioComMesmoEmail.isPresent() && !usuarioComMesmoEmail.get().getId().equals(id)) {
                throw new UsuarioException("Já existe outro usuário cadastrado com o email '" + usuarioAtualizado.getEmail() + "'.");
            }
            
            // Atualizar os campos
            usuarioExistente.setNome(usuarioAtualizado.getNome());
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            usuarioExistente.setSenha(usuarioAtualizado.getSenha());
            usuarioExistente.setPerfil(usuarioAtualizado.getPerfil());
            
            return usuarioRepository.save(usuarioExistente);
            
        } catch (UsuarioException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao atualizar o usuário.", e);
        }
    }

    public void excluir(Integer id) {
        try {
            if (id == null) {
                throw new UsuarioException("ID do usuário não pode ser nulo.");
            }
            
            if (!usuarioRepository.existsById(id)) {
                throw new UsuarioException("Usuário com ID " + id + " não encontrado para exclusão.");
            }
            
            usuarioRepository.deleteById(id);
            
        } catch (UsuarioException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao excluir o usuário.", e);
        }
    }

    // Métodos adicionais usando as queries customizadas do repositório
    public Optional<Usuario> buscarPorEmail(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new UsuarioException("Email não pode estar vazio na busca.");
            }
            return usuarioRepository.findByEmailIgnoreCase(email);
        } catch (UsuarioException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar usuário por email.", e);
        }
    }
    
    public List<Usuario> buscarPorNome(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                throw new UsuarioException("Nome não pode estar vazio na busca.");
            }
            return usuarioRepository.findByNomeContainingIgnoreCase(nome);
        } catch (UsuarioException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar usuários por nome.", e);
        }
    }
    
    public List<Usuario> buscarPorPerfil(PerfilEnum perfil) {
        try {
            if (perfil == null) {
                throw new UsuarioException("Perfil não pode ser nulo na busca.");
            }
            return usuarioRepository.findByPerfil(perfil);
        } catch (UsuarioException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar usuários por perfil.", e);
        }
    }
    
    public boolean existePorEmail(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return false;
            }
            return usuarioRepository.existsByEmailIgnoreCase(email);
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao verificar existência do usuário.", e);
        }
    }
}
