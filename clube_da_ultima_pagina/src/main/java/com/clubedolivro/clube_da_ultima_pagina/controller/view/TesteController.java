package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.Grupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.UsuarioGrupo;
import com.clubedolivro.clube_da_ultima_pagina.entity.Usuario;
import com.clubedolivro.clube_da_ultima_pagina.entity.Perfil;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoService;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioGrupoService;
import com.clubedolivro.clube_da_ultima_pagina.service.UsuarioService;
import com.clubedolivro.clube_da_ultima_pagina.service.PerfilService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TesteController {
    
    private final UsuarioService usuarioService;
    private final GrupoService grupoService;
    private final PerfilService perfilService;
    private final UsuarioGrupoService usuarioGrupoService;

    public TesteController(UsuarioService usuarioService, 
                         GrupoService grupoService, 
                         PerfilService perfilService,
                         UsuarioGrupoService usuarioGrupoService) {
        this.usuarioService = usuarioService;
        this.grupoService = grupoService;
        this.perfilService = perfilService;
        this.usuarioGrupoService = usuarioGrupoService;
    }

    @GetMapping("/teste-grupo")
    public String testarGrupo() {
        // Criar um usuário
        Usuario usuario = new Usuario();
        usuario.setNome("João");
        usuario.setEmail("joao@example.com");
        usuario = usuarioService.salvar(usuario);

        // Criar um grupo com o usuário como líder
        Grupo grupo = new Grupo();
        grupo.setNome("Grupo de Teste");
        grupo.setDescricao("Grupo para teste");
        grupo.setLider(usuario);
        grupo = grupoService.salvar(grupo);

        // Associar o usuário como membro também
        UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
        usuarioGrupo.setUsuario(usuario);
        usuarioGrupo.setGrupo(grupo);
        usuarioGrupo.setPerfil(perfilService.buscarPorNome("Membro"));
        usuarioGrupoService.salvar(usuarioGrupo);

        return "redirect:/usuario_grupo";
    }
}
