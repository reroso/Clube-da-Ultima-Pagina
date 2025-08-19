package com.clubedolivro.clube_da_ultima_pagina.controller.restapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path = "/api/v1")
public class RestApiMainController {

    @GetMapping()
    public ResponseEntity <String> getApiHome() {
        return ResponseEntity.ok("Hello from API");
    }
}
