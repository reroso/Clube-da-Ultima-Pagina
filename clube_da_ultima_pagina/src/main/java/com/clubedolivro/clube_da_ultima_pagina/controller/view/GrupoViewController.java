package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoService;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioService;
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
public class GrupoViewController {
    
    private final GrupoService grupoService;
    private final UsuarioService usuarioService;

    public GrupoViewController(GrupoService grupoService, UsuarioService usuarioService) {
        this.grupoService = grupoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/grupo")
    public String grupoPage(Model model) {
        model.addAttribute("grupoForm", new Grupo());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("editMode", false);
        return "grupo";
    }

    @PostMapping("/grupo")
    public String cadastrarGrupo(@Valid @ModelAttribute("grupoForm") Grupo grupo,
                               @RequestParam("liderId") Integer liderId,
                               BindingResult bindingResult, 
                               Model model) {
        
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("editMode", false);
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro no cadastro! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "grupo";
        }

        try {
            // Buscar o usuário líder pelo ID
            Usuario lider = usuarioService.buscarPorId(liderId);
            grupo.setLider(lider);

            grupoService.salvar(grupo);
            model.addAttribute("sucessoMensagem", "Grupo cadastrado com sucesso! O líder foi promovido automaticamente.");
            model.addAttribute("grupoForm", new Grupo());
            model.addAttribute("editMode", false);
            
            return "grupo";
        } catch (Exception e) {
            model.addAttribute("erroMensagem", "Erro ao cadastrar grupo: " + e.getMessage());
            model.addAttribute("editMode", false);
            return "grupo";
        }
    }

    @GetMapping("/lista-grupos")
    public String listaGrupos(Model model) {
        model.addAttribute("grupos", grupoService.listarTodos());
        return "lista_grupos";
    }

    @GetMapping("/editar-grupo/{id}")
    public String editarGrupoPage(@PathVariable Integer id, Model model) {
        Grupo grupo = grupoService.buscarPorId(id);
        model.addAttribute("grupoForm", grupo);
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("editMode", true);
        return "grupo";
    }

    @PostMapping("/editar-grupo/{id}")
    public String atualizarGrupo(@PathVariable Integer id,
                               @Valid @ModelAttribute("grupoForm") Grupo grupo,
                               @RequestParam("liderId") Integer liderId,
                               BindingResult bindingResult,
                               Model model) {
        
        model.addAttribute("editMode", true);
        model.addAttribute("usuarios", usuarioService.listarTodos());
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro na atualização! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "grupo";
        }

        try {
            // Buscar o usuário líder pelo ID
            Usuario lider = usuarioService.buscarPorId(liderId);
            grupo.setLider(lider);

            grupoService.atualizar(id, grupo);
            model.addAttribute("sucessoMensagem", "Grupo atualizado com sucesso!");
            
            return "redirect:/lista-grupos";
        } catch (Exception e) {
            model.addAttribute("erroMensagem", "Erro ao atualizar grupo: " + e.getMessage());
            return "grupo";
        }
    }

    @PostMapping("/excluir-grupo/{id}")
    public String excluirGrupo(@PathVariable Integer id, Model model) {
        try {
            grupoService.excluir(id);
            model.addAttribute("sucessoMensagem", "Grupo excluído com sucesso!");
            return "redirect:/lista-grupos";
        } catch (Exception e) {
            model.addAttribute("erroMensagem", "Erro ao excluir grupo: " + e.getMessage());
            return "redirect:/lista-grupos";
        }
    }
}
