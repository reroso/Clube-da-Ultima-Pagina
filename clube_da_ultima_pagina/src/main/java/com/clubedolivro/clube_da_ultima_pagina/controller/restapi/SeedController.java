package com.clubedolivro.clube_da_ultima_pagina.controller.restapi;

import com.clubedolivro.clube_da_ultima_pagina.service.SeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seed")
public class SeedController {
    private final SeedService seedService;

    public SeedController(SeedService seedService) {
        this.seedService = seedService;
    }

    @PostMapping
    public ResponseEntity<String> seedDatabase() {
        try {
            seedService.seedDatabase();
            return ResponseEntity.ok("Seed executado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao executar seed: " + e.getMessage());
        }
    }
}
