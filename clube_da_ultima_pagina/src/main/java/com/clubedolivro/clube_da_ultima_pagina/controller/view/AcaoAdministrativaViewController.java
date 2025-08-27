package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.AcaoAdministrativa;
import com.clubedolivro.clube_da_ultima_pagina.service.AcaoAdministrativaService;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class AcaoAdministrativaViewController {

    private final AcaoAdministrativaService acaoAdministrativaService;
    private final UsuarioService usuarioService;

    public AcaoAdministrativaViewController(AcaoAdministrativaService acaoAdministrativaService,
                                          UsuarioService usuarioService) {
        this.acaoAdministrativaService = acaoAdministrativaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/acao_administrativa")
    public String acaoAdministrativaPage(Model model) {
        model.addAttribute("acaoForm", new AcaoAdministrativa());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "acao_administrativa";
    }

    @PostMapping("/acao_administrativa")
    public String registrarAcao(@Valid @ModelAttribute("acaoForm") AcaoAdministrativa acao,
                              BindingResult bindingResult,
                              Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro no registro! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            model.addAttribute("usuarios", usuarioService.listarTodos());
            return "acao_administrativa";
        }

        acaoAdministrativaService.salvar(acao);
        model.addAttribute("sucessoMensagem", "Ação administrativa registrada com sucesso!");
        model.addAttribute("acaoForm", new AcaoAdministrativa());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "acao_administrativa";
    }

    @GetMapping("/lista-acoes")
    public String listaAcoes(Model model) {
        model.addAttribute("acoes", acaoAdministrativaService.listarTodos());
        return "lista_acoes";
    }
}
