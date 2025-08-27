package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.Anotacao;
import com.clubedolivro.clube_da_ultima_pagina.service.AnotacaoService;
import com.clubedolivro.clube_da_ultima_pagina.service.CapituloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class AnotacaoViewController {

    private final AnotacaoService anotacaoService;
    private final CapituloService capituloService;

    public AnotacaoViewController(AnotacaoService anotacaoService, CapituloService capituloService) {
        this.anotacaoService = anotacaoService;
        this.capituloService = capituloService;
    }

    @GetMapping("/anotacao")
    public String anotacaoPage(Model model) {
        model.addAttribute("anotacaoForm", new Anotacao());
        model.addAttribute("capitulos", capituloService.listarTodos());
        return "anotacao";
    }

    @PostMapping("/anotacao")
    public String cadastrarAnotacao(@Valid @ModelAttribute("anotacaoForm") Anotacao anotacao, 
                                  BindingResult bindingResult, 
                                  Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro no cadastro! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            model.addAttribute("capitulos", capituloService.listarTodos());
            return "anotacao";
        }

        anotacaoService.salvar(anotacao);
        model.addAttribute("sucessoMensagem", "Anotação cadastrada com sucesso!");
        model.addAttribute("anotacaoForm", new Anotacao());
        model.addAttribute("capitulos", capituloService.listarTodos());
        return "anotacao";
    }

    @GetMapping("/lista-anotacoes")
    public String listaAnotacoes(Model model) {
        model.addAttribute("anotacoes", anotacaoService.listarTodos());
        return "lista_anotacoes";
    }
}
