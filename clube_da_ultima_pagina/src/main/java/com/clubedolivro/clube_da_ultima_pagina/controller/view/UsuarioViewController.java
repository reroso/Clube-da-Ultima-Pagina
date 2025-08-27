package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.dto.UsuarioDTO;
import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.entity.UsuarioGrupo;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioService;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioGrupoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UsuarioViewController {

    private final UsuarioService usuarioService;
    private final UsuarioGrupoService usuarioGrupoService;

    public UsuarioViewController(UsuarioService usuarioService, UsuarioGrupoService usuarioGrupoService) {
        this.usuarioService = usuarioService;
        this.usuarioGrupoService = usuarioGrupoService;
    }

    @GetMapping("/usuario")
    public String usuarioPage(Model model) {
        model.addAttribute("usuarioForm", new Usuario());
        return "usuario";
    }

    @PostMapping("/usuario")
    public String cadastrarUsuario(@Valid @ModelAttribute("usuarioForm") Usuario usuario, BindingResult bindingResult, Model model) {
        
        if (bindingResult.hasErrors()) {
            // Se há erros, adiciona mensagem de erro e mantém na mesma página
            model.addAttribute("erroMensagem", "Erro no cadastro! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            return "usuario";
        } else {
            try {
                // Salva o usuário
                Usuario usuarioSalvo = usuarioService.salvar(usuario);
                
                // Associa o usuário ao perfil de membro
                usuarioGrupoService.associarUsuarioMembro(usuarioSalvo);
                
                model.addAttribute("sucessoMensagem", "Usuário cadastrado com sucesso como membro!");
                model.addAttribute("usuarioForm", new Usuario()); // Limpa o formulário
                return "usuario";
            } catch (RuntimeException e) {
                model.addAttribute("erroMensagem", "Erro ao cadastrar usuário: " + e.getMessage());
                return "usuario";
            }
        }
    }

    @GetMapping("/lista-usuarios")
    public String listaUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<UsuarioDTO> usuariosComPerfil = usuarios.stream()
            .map(usuario -> {
                UsuarioGrupo usuarioGrupo = usuarioGrupoService.buscarPorUsuario(usuario.getId());
                String perfil = usuarioGrupo != null && usuarioGrupo.getPerfil() != null ? 
                               usuarioGrupo.getPerfil().getNome() : "Não definido";
                return new UsuarioDTO(usuario, perfil);
            })
            .collect(Collectors.toList());
        
        model.addAttribute("usuarios", usuariosComPerfil);
        return "lista_usuarios";
    }
}