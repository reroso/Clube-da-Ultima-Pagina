package com.clubedolivro.clube_da_ultima_pagina.controller.view;


import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.entity.PerfilEnum;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioViewController {

    private final UsuarioService usuarioService;

    public UsuarioViewController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuario")
    public String usuarioPage(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("usuarioForm", new Usuario());
        model.addAttribute("tiposPerfil", PerfilEnum.values());
        return "usuario";
    }

    @PostMapping("/usuario")
    public String cadastrarUsuario(@ModelAttribute("usuarioForm") Usuario usuario) {
        usuarioService.salvar(usuario);
        return "redirect:/usuario";
    }
}