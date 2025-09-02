package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.entity.UsuarioGrupo;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoService;
import com.clubedolivro.clube_da_ultima_pagina.service.PerfilService;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioGrupoService;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EntradaGrupoViewController {
    
    private final GrupoService grupoService;
    private final UsuarioService usuarioService;
    private final UsuarioGrupoService usuarioGrupoService;
    private final PerfilService perfilService;

    public EntradaGrupoViewController(GrupoService grupoService,
                                    UsuarioService usuarioService,
                                    UsuarioGrupoService usuarioGrupoService,
                                    PerfilService perfilService) {
        this.grupoService = grupoService;
        this.usuarioService = usuarioService;
        this.usuarioGrupoService = usuarioGrupoService;
        this.perfilService = perfilService;
    }

    @GetMapping("/entrada-grupo")
    public String entradaGrupoPage(Model model) {
        model.addAttribute("grupos", grupoService.listarTodos());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "entrada_grupo";
    }

    @PostMapping("/entrada-grupo")
    public String processarEntradaGrupo(@RequestParam("idUsuario") Integer idUsuario,
                                      @RequestParam("idGrupo") Integer idGrupo,
                                      Model model) {
        try {
            // Valida usuário
            Usuario usuario = usuarioService.buscarPorId(idUsuario);
            if (usuario == null) {
                model.addAttribute("erroMensagem", "Usuário não encontrado");
                return "entrada_grupo";
            }

            // Valida grupo
            Grupo grupo = grupoService.buscarPorId(idGrupo);
            if (grupo == null) {
                model.addAttribute("erroMensagem", "Grupo não encontrado");
                return "entrada_grupo";
            }

            // Verifica se o usuário já é membro do grupo
            if (usuarioGrupoService.existeAssociacao(usuario.getId(), grupo.getId())) {
                model.addAttribute("erroMensagem", "O usuário já é membro deste grupo");
            } else {
                // Busca o perfil de membro
                var perfilMembro = perfilService.buscarPorNome("Membro");
                if (perfilMembro == null) {
                    model.addAttribute("erroMensagem", "Perfil 'Membro' não encontrado no sistema");
                    return "entrada_grupo";
                }

                // Cria nova associação com perfil de membro
                UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
                usuarioGrupo.setUsuario(usuario);
                usuarioGrupo.setGrupo(grupo);
                usuarioGrupo.setPerfil(perfilMembro);
                usuarioGrupoService.salvar(usuarioGrupo);

                model.addAttribute("sucessoMensagem", "Usuário adicionado ao grupo com sucesso!");
            }
        } catch (Exception e) {
            model.addAttribute("erroMensagem", "Erro ao adicionar usuário ao grupo: " + e.getMessage());
        }

        // Recarrega os dados para o formulário
        model.addAttribute("grupos", grupoService.listarTodos());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "entrada_grupo";
    }
}
