package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AcaoAdministrativaViewController {

    @GetMapping("/acao_administrativa")
    public String acaoAdministrativaPage() {
        return "acao_administrativa.html";
    }
}
