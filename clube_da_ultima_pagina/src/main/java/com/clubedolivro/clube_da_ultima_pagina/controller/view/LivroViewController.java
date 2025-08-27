package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.Livro;
import com.clubedolivro.clube_da_ultima_pagina.service.LivroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class LivroViewController {
    
    private final LivroService livroService;

    public LivroViewController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/livro")
    public String livroPage(Model model) {
        model.addAttribute("livroForm", new Livro());
        return "livro";
    }

    @PostMapping("/livro")
    public String cadastrarLivro(@Valid @ModelAttribute("livroForm") Livro livro, 
                               BindingResult bindingResult, 
                               Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro no cadastro! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "livro";
        }

        livroService.salvar(livro);
        model.addAttribute("sucessoMensagem", "Livro cadastrado com sucesso!");
        model.addAttribute("livroForm", new Livro());
        return "livro";
    }

    @GetMapping("/lista-livros")
    public String listaLivros(Model model) {
        model.addAttribute("livros", livroService.listarTodos());
        return "lista_livros";
    }
}
