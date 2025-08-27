package com.clubedolivro.clube_da_ultima_pagina.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoService;

@Component
public class StringToGrupoConverter implements Converter<String, Grupo> {

    private final GrupoService grupoService;

    public StringToGrupoConverter(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @Override
    public Grupo convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        return grupoService.buscarPorId(Integer.valueOf(source));
    }
}
