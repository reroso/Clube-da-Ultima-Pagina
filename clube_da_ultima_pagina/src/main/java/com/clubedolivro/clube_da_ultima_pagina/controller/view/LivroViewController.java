package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LivroViewController {
    
    @GetMapping("/livro")
    public String livroPage() {
        return "livro.html"; 
    }
}
