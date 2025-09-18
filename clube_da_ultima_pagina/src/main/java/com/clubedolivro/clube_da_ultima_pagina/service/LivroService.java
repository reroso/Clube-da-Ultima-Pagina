package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Livro;
import com.clubedolivro.clube_da_ultima_pagina.exception.LivroException;
import com.clubedolivro.clube_da_ultima_pagina.exception.ClubeLivroException;
import com.clubedolivro.clube_da_ultima_pagina.repository.LivroRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro salvar(Livro livro) {
        try {
            // Validações específicas de negócio
            if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
                throw new LivroException("O título do livro não pode estar vazio.");
            }
            
            if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
                throw new LivroException("O autor do livro não pode estar vazio.");
            }
            
            // Verificar se já existe um livro com o mesmo título
            if (existePorTitulo(livro.getTitulo())) {
                throw new LivroException("Já existe um livro cadastrado com o título '" + livro.getTitulo() + "'.");
            }
            
            return livroRepository.save(livro);
            
        } catch (LivroException e) {
            // Re-lança exceções de negócio
            throw e;
        } catch (Exception e) {
            // Converte outras exceções em exceção geral da aplicação
            throw new ClubeLivroException("Erro interno ao salvar o livro. Tente novamente.", e);
        }
    }

    public List<Livro> listarTodos() {
        try {
            return livroRepository.findAll();
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar a lista de livros.", e);
        }
    }

    public Livro buscarPorId(Integer id) {
        try {
            if (id == null) {
                throw new LivroException("ID do livro não pode ser nulo.");
            }
            
            Optional<Livro> livro = livroRepository.findById(id);
            if (livro.isEmpty()) {
                throw new LivroException("Livro com ID " + id + " não encontrado.");
            }
            
            return livro.get();
            
        } catch (LivroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar livro por ID.", e);
        }
    }

    public Livro atualizar(Integer id, Livro livroAtualizado) {
        try {
            if (id == null) {
                throw new LivroException("ID do livro não pode ser nulo para atualização.");
            }
            
            // Buscar o livro existente
            Livro livroExistente = buscarPorId(id);
            
            // Validações específicas de negócio
            if (livroAtualizado.getTitulo() == null || livroAtualizado.getTitulo().trim().isEmpty()) {
                throw new LivroException("O título do livro não pode estar vazio.");
            }
            
            if (livroAtualizado.getAutor() == null || livroAtualizado.getAutor().trim().isEmpty()) {
                throw new LivroException("O autor do livro não pode estar vazio.");
            }
            
            // Verificar se já existe outro livro com o mesmo título (exceto o atual)
            Optional<Livro> livroComMesmoTitulo = buscarPorTitulo(livroAtualizado.getTitulo());
            if (livroComMesmoTitulo.isPresent() && !livroComMesmoTitulo.get().getId().equals(id)) {
                throw new LivroException("Já existe outro livro cadastrado com o título '" + livroAtualizado.getTitulo() + "'.");
            }
            
            // Atualizar os campos
            livroExistente.setTitulo(livroAtualizado.getTitulo());
            livroExistente.setAutor(livroAtualizado.getAutor());
            livroExistente.setDescricao(livroAtualizado.getDescricao());
            
            return livroRepository.save(livroExistente);
            
        } catch (LivroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao atualizar o livro.", e);
        }
    }

    public void excluir(Integer id) {
        try {
            if (id == null) {
                throw new LivroException("ID do livro não pode ser nulo.");
            }
            
            if (!livroRepository.existsById(id)) {
                throw new LivroException("Livro com ID " + id + " não encontrado para exclusão.");
            }
            
            livroRepository.deleteById(id);
            
        } catch (LivroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro interno ao excluir o livro.", e);
        }
    }

    // Métodos adicionais usando as queries customizadas do repositório
    public Optional<Livro> buscarPorTitulo(String titulo) {
        try {
            if (titulo == null || titulo.trim().isEmpty()) {
                throw new LivroException("Título não pode estar vazio na busca.");
            }
            return livroRepository.findByTituloIgnoreCase(titulo);
        } catch (LivroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar livro por título.", e);
        }
    }
    
    public List<Livro> buscarPorAutor(String autor) {
        try {
            if (autor == null || autor.trim().isEmpty()) {
                throw new LivroException("Nome do autor não pode estar vazio na busca.");
            }
            return livroRepository.findByAutorContainingIgnoreCase(autor);
        } catch (LivroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar livros por autor.", e);
        }
    }
    
    public List<Livro> buscarPorTituloContendo(String titulo) {
        try {
            if (titulo == null || titulo.trim().isEmpty()) {
                throw new LivroException("Termo de busca não pode estar vazio.");
            }
            return livroRepository.findByTituloContainingIgnoreCase(titulo);
        } catch (LivroException e) {
            throw e;
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao buscar livros por título.", e);
        }
    }
    
    public boolean existePorTitulo(String titulo) {
        try {
            if (titulo == null || titulo.trim().isEmpty()) {
                return false;
            }
            return livroRepository.existsByTituloIgnoreCase(titulo);
        } catch (Exception e) {
            throw new ClubeLivroException("Erro ao verificar existência do livro.", e);
        }
    }
}
