package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioGrupoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioGrupoViewController {
    
    private final UsuarioGrupoService usuarioGrupoService;

    public UsuarioGrupoViewController(UsuarioGrupoService usuarioGrupoService) {
        this.usuarioGrupoService = usuarioGrupoService;
    }

    @GetMapping("/usuario_grupo")
    public String usuarioGrupoPage(Model model) {
        model.addAttribute("usuarioGrupos", usuarioGrupoService.listarTodos());
        return "usuario_grupo";
    }
}
