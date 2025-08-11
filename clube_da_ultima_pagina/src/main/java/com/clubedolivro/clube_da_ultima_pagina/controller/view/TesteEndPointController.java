package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping(path = "teste_endpoint")
public class TesteEndPointController {
    @GetMapping("/{id}")
    public String getHomePage(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("nomeGrupo", "Terror Futebol Clube (Esse atributo vem do controller)");
        return "teste_endpoint";
    }
}
