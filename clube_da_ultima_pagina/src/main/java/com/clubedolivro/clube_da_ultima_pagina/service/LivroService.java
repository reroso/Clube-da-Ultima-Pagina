package com.clubedolivro.clube_da_ultima_pagina.service;

import com.clubedolivro.clube_da_ultima_pagina.entity.Livro;
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
        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Integer id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.orElse(null);
    }

    public void excluir(Integer id) {
        livroRepository.deleteById(id);
    }

    // Métodos adicionais usando as queries customizadas do repositório
    public Optional<Livro> buscarPorTitulo(String titulo) {
        return livroRepository.findByTituloIgnoreCase(titulo);
    }
    
    public List<Livro> buscarPorAutor(String autor) {
        return livroRepository.findByAutorContainingIgnoreCase(autor);
    }
    
    public List<Livro> buscarPorTituloContendo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    public boolean existePorTitulo(String titulo) {
        return livroRepository.existsByTituloIgnoreCase(titulo);
    }
}
