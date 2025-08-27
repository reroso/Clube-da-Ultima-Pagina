package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.Voto;
import com.clubedolivro.clube_da_ultima_pagina.service.VotoService;
import com.clubedolivro.clube_da_ultima_pagina.service.SugestaoLivroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class VotoViewController {
    
    private final VotoService votoService;
    private final SugestaoLivroService sugestaoLivroService;

    public VotoViewController(VotoService votoService, SugestaoLivroService sugestaoLivroService) {
        this.votoService = votoService;
        this.sugestaoLivroService = sugestaoLivroService;
    }

    @GetMapping("/voto")
    public String votoPage(Model model) {
        model.addAttribute("votoForm", new Voto());
        model.addAttribute("sugestoes", sugestaoLivroService.listarTodos());
        return "voto";
    }

    @PostMapping("/voto")
    public String cadastrarVoto(@Valid @ModelAttribute("votoForm") Voto voto,
                              BindingResult bindingResult,
                              Model model) {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("erroMensagem", "Erro no cadastro! Verifique os campos abaixo:");
            model.addAttribute("erros", bindingResult.getAllErrors());
            model.addAttribute("sugestoes", sugestaoLivroService.listarTodos());
            return "voto";
        }

        votoService.salvar(voto);
        model.addAttribute("sucessoMensagem", "Voto registrado com sucesso!");
        model.addAttribute("votoForm", new Voto());
        model.addAttribute("sugestoes", sugestaoLivroService.listarTodos());
        return "voto";
    }

    @GetMapping("/lista-votos")
    public String listaVotos(Model model) {
        model.addAttribute("votos", votoService.listarTodos());
        return "lista_votos";
    }
}
