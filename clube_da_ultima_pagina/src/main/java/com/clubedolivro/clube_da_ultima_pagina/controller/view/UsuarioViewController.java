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
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.Valid;

@Controller
public class UsuarioViewController {
    
    private final UsuarioService usuarioService;

    public UsuarioViewController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuario")
    public String usuarioPage(Model model) {
        Usuario usuario = new Usuario();
        usuario.setPerfil(PerfilEnum.MEMBRO); // Define MEMBRO como padrão
        model.addAttribute("usuarioForm", usuario);
        return "usuario";
    }

    @PostMapping("/usuario")
    public String cadastrarUsuario(@Valid @ModelAttribute("usuarioForm") Usuario usuario, 
                                 BindingResult bindingResult, 
                                 Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro no cadastro! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "usuario";
        }

        // Garantir que o usuário sempre seja MEMBRO ao se cadastrar
        usuario.setPerfil(PerfilEnum.MEMBRO);
        
        // As exceções agora são tratadas no GlobalExceptionHandler
        usuarioService.salvar(usuario);
        model.addAttribute("sucessoMensagem", "Usuário cadastrado com sucesso como membro!");
        
        Usuario novoUsuario = new Usuario();
        novoUsuario.setPerfil(PerfilEnum.MEMBRO);
        model.addAttribute("usuarioForm", novoUsuario);
        
        return "usuario";
    }

    @GetMapping("/lista-usuarios")
    public String listaUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "lista_usuarios";
    }

    @GetMapping("/editar-usuario/{id}")
    public String editarUsuarioPage(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuarioForm", usuario);
        model.addAttribute("perfis", PerfilEnum.values()); // Apenas para administradores editarem
        model.addAttribute("editMode", true);
        return "usuario";
    }

    @PostMapping("/editar-usuario/{id}")
    public String atualizarUsuario(@PathVariable Integer id,
                                 @Valid @ModelAttribute("usuarioForm") Usuario usuario,
                                 BindingResult bindingResult,
                                 Model model) {
        
        model.addAttribute("editMode", true);
        model.addAttribute("perfis", PerfilEnum.values());
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro na atualização! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "usuario";
        }

        // As exceções são tratadas no GlobalExceptionHandler
        usuarioService.atualizar(id, usuario);
        model.addAttribute("sucessoMensagem", "Usuário atualizado com sucesso!");
        
        return "redirect:/lista-usuarios";
    }

    @PostMapping("/excluir-usuario/{id}")
    public String excluirUsuario(@PathVariable Integer id, Model model) {
        // As exceções são tratadas no GlobalExceptionHandler
        usuarioService.excluir(id);
        model.addAttribute("sucessoMensagem", "Usuário excluído com sucesso!");
        return "redirect:/lista-usuarios";
    }
}