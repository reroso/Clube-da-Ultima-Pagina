package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.entity.UsuarioGrupo;
import com.clubedolivro.clube_da_ultima_pagina.exception.UsuarioGrupoException;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoService;
import com.clubedolivro.clube_da_ultima_pagina.service.PerfilService;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioGrupoService;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.List;

@Controller
public class EntradaGrupoViewController {
    
    private final GrupoService grupoService;
    private final UsuarioService usuarioService;
    private final UsuarioGrupoService usuarioGrupoService;
    // Mantemos perfilService para funcionalidades futuras
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
    public String entradaGrupoPage(@RequestParam(required = false) Integer id, Model model) {
        try {
            UsuarioGrupo usuarioGrupoForm;
            boolean editMode = false;
            
            if (id != null) {
                usuarioGrupoForm = usuarioGrupoService.buscarPorId(id);
                editMode = true;
            } else {
                usuarioGrupoForm = new UsuarioGrupo();
            }
            
            model.addAttribute("usuarioGrupoForm", usuarioGrupoForm);
            model.addAttribute("grupos", grupoService.listarTodos());
            model.addAttribute("usuarios", usuarioService.listarTodos());
            model.addAttribute("editMode", editMode);
            
            return "entrada_grupo";
            
        } catch (UsuarioGrupoException e) {
            model.addAttribute("erroMensagem", e.getMessage());
            model.addAttribute("usuarioGrupoForm", new UsuarioGrupo());
            model.addAttribute("grupos", grupoService.listarTodos());
            model.addAttribute("usuarios", usuarioService.listarTodos());
            model.addAttribute("editMode", false);
            return "entrada_grupo";
        }
    }

    @PostMapping("/entrada-grupo")
    public String processarEntradaGrupo(@Valid @ModelAttribute("usuarioGrupoForm") UsuarioGrupo usuarioGrupo,
                                      BindingResult bindingResult,
                                      @RequestParam(required = false) Boolean editMode,
                                      Model model) {
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("erroMensagem", "Erro na validação! Verifique os campos abaixo:");
                model.addAttribute("erros", bindingResult.getAllErrors());
                model.addAttribute("grupos", grupoService.listarTodos());
                model.addAttribute("usuarios", usuarioService.listarTodos());
                model.addAttribute("editMode", editMode != null && editMode);
                return "entrada_grupo";
            }

            if (editMode != null && editMode && usuarioGrupo.getId() != null) {
                usuarioGrupoService.atualizar(usuarioGrupo.getId(), usuarioGrupo);
                model.addAttribute("sucessoMensagem", "Associação atualizada com sucesso!");
            } else {
                usuarioGrupoService.salvar(usuarioGrupo);
                model.addAttribute("sucessoMensagem", "Usuário adicionado ao grupo com sucesso!");
            }
            
            model.addAttribute("usuarioGrupoForm", new UsuarioGrupo());
            model.addAttribute("grupos", grupoService.listarTodos());
            model.addAttribute("usuarios", usuarioService.listarTodos());
            model.addAttribute("perfis", perfilService.listarTodos());
            model.addAttribute("editMode", false);
            return "entrada_grupo";
            
        } catch (UsuarioGrupoException e) {
            model.addAttribute("erroMensagem", e.getMessage());
            model.addAttribute("grupos", grupoService.listarTodos());
            model.addAttribute("usuarios", usuarioService.listarTodos());
            model.addAttribute("editMode", editMode != null && editMode);
            return "entrada_grupo";
        }
    }

    @GetMapping("/lista-usuario-grupo")
    public String listaUsuarioGrupo(Model model) {
        List<UsuarioGrupo> associacoes = usuarioGrupoService.listarTodos();
        model.addAttribute("usuarioGrupos", associacoes);
        
        // Calcular estatísticas
        long totalLideres = associacoes.stream()
                .filter(ug -> "Líder".equals(ug.getPerfil().getNome()))
                .count();
        long totalMembros = associacoes.stream()
                .filter(ug -> "Membro".equals(ug.getPerfil().getNome()))
                .count();
                
        model.addAttribute("totalAssociacoes", associacoes.size());
        model.addAttribute("totalLideres", totalLideres);
        model.addAttribute("totalMembros", totalMembros);
        
        return "lista_usuario_grupo";
    }
    
    @PostMapping("/usuario-grupo/sair/{id}")
    public String sairDoGrupo(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            usuarioGrupoService.sairDoGrupo(id);
            redirectAttributes.addFlashAttribute("sucessoMensagem", "Usuário removido do grupo com sucesso!");
        } catch (UsuarioGrupoException e) {
            redirectAttributes.addFlashAttribute("erroMensagem", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erroMensagem", "Erro interno ao remover usuário do grupo.");
        }
        
        return "redirect:/lista-usuario-grupo";
    }

    // Método legado para compatibilidade
    @PostMapping("/entrada-grupo-simples")
    public String processarEntradaGrupoSimples(@RequestParam("idUsuario") Integer idUsuario,
                                             @RequestParam("idGrupo") Integer idGrupo,
                                             Model model) {
        try {
            // Buscar entidades
            Usuario usuario = usuarioService.buscarPorId(idUsuario);
            Grupo grupo = grupoService.buscarPorId(idGrupo);

            // Usar o novo método do service que atribui automaticamente "Membro"
            usuarioGrupoService.entrarNoGrupo(usuario, grupo);
            model.addAttribute("sucessoMensagem", "Usuário adicionado ao grupo com sucesso!");
            
        } catch (UsuarioGrupoException e) {
            model.addAttribute("erroMensagem", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("erroMensagem", "Erro ao adicionar usuário ao grupo: " + e.getMessage());
        }

        // Recarregar dados
        model.addAttribute("grupos", grupoService.listarTodos());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("usuarioGrupoForm", new UsuarioGrupo());
        model.addAttribute("editMode", false);
        return "entrada_grupo";
    }
}
