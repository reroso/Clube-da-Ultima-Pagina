package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.SugestaoLivro;
import com.clubedolivro.clube_da_ultima_pagina.service.SugestaoLivroService;
import com.clubedolivro.clube_da_ultima_pagina.service.LivroService;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class SugestaoLivroViewController {
    
    private final SugestaoLivroService sugestaoLivroService;
    private final LivroService livroService;
    private final GrupoService grupoService;

    public SugestaoLivroViewController(SugestaoLivroService sugestaoLivroService,
                                     LivroService livroService,
                                     GrupoService grupoService) {
        this.sugestaoLivroService = sugestaoLivroService;
        this.livroService = livroService;
        this.grupoService = grupoService;
    }

    @GetMapping("/sugestao_livro")
    public String sugestaoLivroPage(Model model) {
        model.addAttribute("sugestaoForm", new SugestaoLivro());
        model.addAttribute("livros", livroService.listarTodos());
        model.addAttribute("grupos", grupoService.listarTodos());
        return "sugestao_livro";
    }

    @PostMapping("/sugestao_livro")
    public String cadastrarSugestao(@Valid @ModelAttribute("sugestaoForm") SugestaoLivro sugestao,
                                  BindingResult bindingResult,
                                  Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro no cadastro! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            model.addAttribute("livros", livroService.listarTodos());
            model.addAttribute("grupos", grupoService.listarTodos());
            return "sugestao_livro";
        }

        sugestaoLivroService.salvar(sugestao);
        model.addAttribute("sucessoMensagem", "Sugestão de livro cadastrada com sucesso!");
        model.addAttribute("sugestaoForm", new SugestaoLivro());
        model.addAttribute("livros", livroService.listarTodos());
        model.addAttribute("grupos", grupoService.listarTodos());
        return "sugestao_livro";
    }

    @GetMapping("/lista-sugestoes")
    public String listaSugestoes(Model model) {
        model.addAttribute("sugestoes", sugestaoLivroService.listarTodos());
        return "lista_sugestoes";
    }
}
