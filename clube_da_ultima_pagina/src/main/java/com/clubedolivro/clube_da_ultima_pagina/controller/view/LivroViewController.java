package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.Livro;
import com.clubedolivro.clube_da_ultima_pagina.service.LivroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        // As exceções agora são tratadas no GlobalExceptionHandler
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

    @GetMapping("/editar-livro/{id}")
    public String editarLivroPage(@PathVariable Integer id, Model model) {
        Livro livro = livroService.buscarPorId(id);
        model.addAttribute("livroForm", livro);
        model.addAttribute("editMode", true);
        return "livro";
    }

    @PostMapping("/editar-livro/{id}")
    public String atualizarLivro(@PathVariable Integer id,
                               @Valid @ModelAttribute("livroForm") Livro livro,
                               BindingResult bindingResult,
                               Model model) {
        
        model.addAttribute("editMode", true);
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro na atualização! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "livro";
        }

        // As exceções são tratadas no GlobalExceptionHandler
        livroService.atualizar(id, livro);
        model.addAttribute("sucessoMensagem", "Livro atualizado com sucesso!");
        
        return "redirect:/lista-livros";
    }

    @PostMapping("/excluir-livro/{id}")
    public String excluirLivro(@PathVariable Integer id, Model model) {
        // As exceções são tratadas no GlobalExceptionHandler
        livroService.excluir(id);
        model.addAttribute("sucessoMensagem", "Livro excluído com sucesso!");
        return "redirect:/lista-livros";
    }
}
