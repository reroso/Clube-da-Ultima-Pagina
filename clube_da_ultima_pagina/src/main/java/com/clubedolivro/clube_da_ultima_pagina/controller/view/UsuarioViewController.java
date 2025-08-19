package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.entity.PerfilEnum;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class UsuarioViewController {

    private final UsuarioService usuarioService;

    public UsuarioViewController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuario")
    public String usuarioPage(Model model) {
        model.addAttribute("usuarioForm", new Usuario());
        model.addAttribute("tiposPerfil", PerfilEnum.values());
        return "usuario";
    }

    @PostMapping("/usuario")
    public String cadastrarUsuario(@Valid @ModelAttribute("usuarioForm") Usuario usuario, BindingResult bindingResult, Model model) {

        model.addAttribute("tiposPerfil", PerfilEnum.values());
        
        if (bindingResult.hasErrors()) {
            // Se há erros, adiciona mensagem de erro e mantém na mesma página
            model.addAttribute("erroMensagem", "Erro no cadastro! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "usuario";
        } else {
            // Se não há erros, salva o usuário e mostra mensagem de sucesso
            usuarioService.salvar(usuario);
            model.addAttribute("sucessoMensagem", "Usuário cadastrado com sucesso!");
            model.addAttribute("usuarioForm", new Usuario()); // Limpa o formulário
            return "usuario";
        }
    }

    @GetMapping("/lista-usuarios")
    public String listaUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "lista_usuarios";
    }
}