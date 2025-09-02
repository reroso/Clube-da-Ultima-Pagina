package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping(path = "/helloworld")
public class MainViewController {
    @GetMapping("/")
    public String getHomePage() {
        return "helloworld.html";
    }
    
}
