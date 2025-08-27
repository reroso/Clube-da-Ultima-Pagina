package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.Encontro;
import com.clubedolivro.clube_da_ultima_pagina.service.EncontroService;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class EncontroViewController {
    
    private final EncontroService encontroService;
    private final GrupoService grupoService;

    public EncontroViewController(EncontroService encontroService, GrupoService grupoService) {
        this.encontroService = encontroService;
        this.grupoService = grupoService;
    }

    @GetMapping("/encontro")
    public String encontroPage(Model model) {
        model.addAttribute("encontroForm", new Encontro());
        model.addAttribute("grupos", grupoService.listarTodos());
        return "encontro";
    }

    @PostMapping("/encontro")
    public String cadastrarEncontro(@Valid @ModelAttribute("encontroForm") Encontro encontro,
                                  BindingResult bindingResult,
                                  Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro no cadastro! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            model.addAttribute("grupos", grupoService.listarTodos());
            return "encontro";
        }

        encontroService.salvar(encontro);
        model.addAttribute("sucessoMensagem", "Encontro cadastrado com sucesso!");
        model.addAttribute("encontroForm", new Encontro());
        model.addAttribute("grupos", grupoService.listarTodos());
        return "encontro";
    }

    @GetMapping("/lista-encontros")
    public String listaEncontros(Model model) {
        model.addAttribute("encontros", encontroService.listarTodos());
        return "lista_encontros";
    }
}
