package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.service.PerfilService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PerfilViewController {

    private final PerfilService perfilService;

    public PerfilViewController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @GetMapping("/perfil")
    public String perfilPage(Model model) {
        model.addAttribute("perfis", perfilService.listarTodos());
        return "perfil";
    }
}
