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
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("editMode", false);
        return "grupo_livro";
    }

    @PostMapping("/grupo-livro")
    public String associarGrupoLivro(@Valid @ModelAttribute("grupoLivroForm") GrupoLivro grupoLivro,
                                   @RequestParam("grupoId") Integer grupoId,
                                   @RequestParam("livroId") Integer livroId,
                                   BindingResult bindingResult, 
                                   Model model) {
        
        model.addAttribute("grupos", grupoService.listarTodos());
        model.addAttribute("livros", livroService.listarTodos());
        model.addAttribute("editMode", false);
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro na associação! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "grupo_livro";
        }

        try {
            // Buscar o grupo pelo ID
            Grupo grupo = grupoService.buscarPorId(grupoId);
            grupoLivro.setGrupo(grupo);

            // Buscar o livro pelo ID
            Livro livro = livroService.buscarPorId(livroId);
            grupoLivro.setLivro(livro);

            grupoLivroService.salvar(grupoLivro);
            model.addAttribute("sucessoMensagem", "Livro associado ao grupo com sucesso!");
            model.addAttribute("grupoLivroForm", new GrupoLivro());
            
            return "grupo_livro";
        } catch (Exception e) {
            model.addAttribute("erroMensagem", "Erro ao associar grupo e livro: " + e.getMessage());
            return "grupo_livro";
        }
    }

    @GetMapping("/lista-grupo-livro")
    public String listaGrupoLivro(Model model) {
        model.addAttribute("grupoLivros", grupoLivroService.listarTodosDTO());
        return "lista_grupo_livro";
    }

    @GetMapping("/editar-grupo-livro/{id}")
    public String editarGrupoLivroPage(@PathVariable Integer id, Model model) {
        try {
            GrupoLivro grupoLivro = grupoLivroService.buscarPorId(id);
            model.addAttribute("grupoLivroForm", grupoLivro);
            model.addAttribute("grupos", grupoService.listarTodos());
            model.addAttribute("livros", livroService.listarTodos());
            model.addAttribute("editMode", true);
            return "grupo_livro";
        } catch (Exception e) {
            model.addAttribute("erroMensagem", "Erro ao buscar associação: " + e.getMessage());
            return "redirect:/lista-grupo-livro";
        }
    }

    @PostMapping("/editar-grupo-livro/{id}")
    public String atualizarGrupoLivro(@PathVariable Integer id,
                                    @Valid @ModelAttribute("grupoLivroForm") GrupoLivro grupoLivro,
                                    @RequestParam("grupoId") Integer grupoId,
                                    @RequestParam("livroId") Integer livroId,
                                    BindingResult bindingResult,
                                    Model model) {
        
        model.addAttribute("editMode", true);
        model.addAttribute("grupos", grupoService.listarTodos());
        model.addAttribute("livros", livroService.listarTodos());
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro na atualização! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "grupo_livro";
        }

        try {
            // Buscar o grupo pelo ID
            Grupo grupo = grupoService.buscarPorId(grupoId);
            grupoLivro.setGrupo(grupo);

            // Buscar o livro pelo ID
            Livro livro = livroService.buscarPorId(livroId);
            grupoLivro.setLivro(livro);

            grupoLivroService.atualizar(id, grupoLivro);
            model.addAttribute("sucessoMensagem", "Associação atualizada com sucesso!");
            
            return "redirect:/lista-grupo-livro";
        } catch (Exception e) {
            model.addAttribute("erroMensagem", "Erro ao atualizar associação: " + e.getMessage());
            return "grupo_livro";
        }
    }

    @PostMapping("/excluir-grupo-livro/{id}")
    public String excluirGrupoLivro(@PathVariable Integer id, Model model) {
        try {
            grupoLivroService.excluir(id);
            model.addAttribute("sucessoMensagem", "Associação excluída com sucesso!");
            return "redirect:/lista-grupo-livro";
        } catch (Exception e) {
            model.addAttribute("erroMensagem", "Erro ao excluir associação: " + e.getMessage());
            return "redirect:/lista-grupo-livro";
        }
    }
}
