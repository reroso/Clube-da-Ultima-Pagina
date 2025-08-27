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
        return "grupo";
    }

    @PostMapping("/grupo")
    public String cadastrarGrupo(@Valid @ModelAttribute("grupoForm") Grupo grupo,
                               @RequestParam("liderId") Integer liderId,
                               BindingResult bindingResult, 
                               Model model) {
        
        // Sempre adiciona a lista de usuários, independente de erro
        model.addAttribute("usuarios", usuarioService.listarTodos());
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro no cadastro! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "grupo";
        }

        // Busca o usuário líder pelo ID
        Usuario lider = usuarioService.buscarPorId(liderId);
        if (lider == null) {
            model.addAttribute("erroMensagem", "Líder selecionado não foi encontrado!");
            return "grupo";
        }

        grupo.setLider(lider);
        grupoService.salvar(grupo);
        model.addAttribute("sucessoMensagem", "Grupo cadastrado com sucesso!");
        model.addAttribute("grupoForm", new Grupo());
        return "grupo";
    }

    @GetMapping("/lista-grupos")
    public String listaGrupos(Model model) {
        model.addAttribute("grupos", grupoService.listarTodos());
        return "lista_grupos";
    }
}
