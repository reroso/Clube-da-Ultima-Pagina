package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.GrupoLivro;
import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Livro;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoLivroService;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoService;
import com.clubedolivro.clube_da_ultima_pagina.service.LivroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

@Controller
public class GrupoLivroViewController {

    private final GrupoLivroService grupoLivroService;
    private final GrupoService grupoService;
    private final LivroService livroService;

    public GrupoLivroViewController(GrupoLivroService grupoLivroService, 
                                  GrupoService grupoService, 
                                  LivroService livroService) {
        this.grupoLivroService = grupoLivroService;
        this.grupoService = grupoService;
        this.livroService = livroService;
    }

    @GetMapping("/grupo-livro")
    public String grupoLivroPage(Model model) {
        model.addAttribute("grupoLivroForm", new GrupoLivro());
        model.addAttribute("grupos", grupoService.listarTodos());
        model.addAttribute("livros", livroService.listarTodos());
        return "grupo_livro";
    }

    @PostMapping("/grupo-livro")
    public String associarGrupoLivro(@Valid @ModelAttribute("grupoLivroForm") GrupoLivro grupoLivro,
                                   @RequestParam("grupoId") Integer grupoId,
                                   @RequestParam("livroId") Integer livroId,
                                   BindingResult bindingResult, 
                                   Model model) {
        
        // Sempre adiciona as listas, independente de erro
        model.addAttribute("grupos", grupoService.listarTodos());
        model.addAttribute("livros", livroService.listarTodos());
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro na associação! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "grupo_livro";
        }

        // Busca o grupo pelo ID
        Grupo grupo = grupoService.buscarPorId(grupoId);
        if (grupo == null) {
            model.addAttribute("erroMensagem", "Grupo selecionado não foi encontrado!");
            return "grupo_livro";
        }

        // Busca o livro pelo ID
        Livro livro = livroService.buscarPorId(livroId);
        if (livro == null) {
            model.addAttribute("erroMensagem", "Livro selecionado não foi encontrado!");
            return "grupo_livro";
        }

        // Verifica se a associação já existe
        if (grupoLivroService.existeAssociacao(grupoId, livroId)) {
            model.addAttribute("erroMensagem", "Esta associação entre grupo e livro já existe!");
            return "grupo_livro";
        }

        grupoLivro.setGrupo(grupo);
        grupoLivro.setLivro(livro);
        grupoLivroService.salvar(grupoLivro);
        
        model.addAttribute("sucessoMensagem", "Livro associado ao grupo com sucesso!");
        model.addAttribute("grupoLivroForm", new GrupoLivro());
        return "grupo_livro";
    }

    @GetMapping("/lista-grupo-livro")
    public String listaGrupoLivro(Model model) {
        model.addAttribute("grupoLivros", grupoLivroService.listarTodosDTO());
        return "lista_grupo_livro";
    }
}
