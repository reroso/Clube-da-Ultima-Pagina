package com.clubedolivro.clube_da_ultima_pagina.controller.view;

import com.clubedolivro.clube_da_ultima_pagina.entity.Encontro;
import com.clubedolivro.clube_da_ultima_pagina.exception.EncontroException;
import com.clubedolivro.clube_da_ultima_pagina.service.EncontroService;
import com.clubedolivro.clube_da_ultima_pagina.service.GrupoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EncontroViewController {
    
    private final EncontroService encontroService;
    private final GrupoService grupoService;

    public EncontroViewController(EncontroService encontroService, GrupoService grupoService) {
        this.encontroService = encontroService;
        this.grupoService = grupoService;
    }

    @GetMapping("/encontro")
    public String encontroPage(@RequestParam(required = false) Integer id, Model model) {
        try {
            Encontro encontro;
            boolean editMode = false;
            
            if (id != null) {
                encontro = encontroService.buscarPorId(id);
                editMode = true;
            } else {
                encontro = new Encontro();
            }
            
            model.addAttribute("encontroForm", encontro);
            model.addAttribute("grupos", grupoService.listarTodos());
            model.addAttribute("editMode", editMode);
            
            return "encontro";
            
        } catch (EncontroException e) {
            model.addAttribute("erroMensagem", e.getMessage());
            model.addAttribute("encontroForm", new Encontro());
            model.addAttribute("grupos", grupoService.listarTodos());
            model.addAttribute("editMode", false);
            return "encontro";
        }
    }

    @PostMapping("/encontro")
    public String salvarEncontro(@Valid @ModelAttribute("encontroForm") Encontro encontro,
                                BindingResult bindingResult,
                                @RequestParam(required = false) Boolean editMode,
                                Model model) {
        
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("erroMensagem", "Erro na validação! Verifique os campos abaixo:");
                model.addAttribute("erros", bindingResult.getAllErrors());
                model.addAttribute("grupos", grupoService.listarTodos());
                model.addAttribute("editMode", editMode != null && editMode);
                return "encontro";
            }

            if (editMode != null && editMode && encontro.getId() != null) {
                encontroService.atualizar(encontro.getId(), encontro);
                model.addAttribute("sucessoMensagem", "Encontro atualizado com sucesso!");
            } else {
                encontroService.salvar(encontro);
                model.addAttribute("sucessoMensagem", "Encontro cadastrado com sucesso!");
            }
            
            model.addAttribute("encontroForm", new Encontro());
            model.addAttribute("grupos", grupoService.listarTodos());
            model.addAttribute("editMode", false);
            return "encontro";
            
        } catch (EncontroException e) {
            model.addAttribute("erroMensagem", e.getMessage());
            model.addAttribute("grupos", grupoService.listarTodos());
            model.addAttribute("editMode", editMode != null && editMode);
            return "encontro";
        }
    }

    @GetMapping("/lista-encontros")
    public String listaEncontros(Model model) {
        List<Encontro> encontros = encontroService.listarTodos();
        model.addAttribute("encontros", encontros);
        
        // Calcular estatísticas
        long encontrosFuturos = encontros.stream()
                .filter(e -> e.getDataHora().isAfter(LocalDateTime.now()))
                .count();
        long encontrosPassados = encontros.stream()
                .filter(e -> e.getDataHora().isBefore(LocalDateTime.now()))
                .count();
                
        model.addAttribute("totalEncontros", encontros.size());
        model.addAttribute("encontrosFuturos", encontrosFuturos);
        model.addAttribute("encontrosPassados", encontrosPassados);
        
        return "lista_encontros";
    }
    
    @PostMapping("/encontro/excluir/{id}")
    public String excluirEncontro(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            encontroService.excluir(id);
            redirectAttributes.addFlashAttribute("sucessoMensagem", "Encontro excluído com sucesso!");
        } catch (EncontroException e) {
            redirectAttributes.addFlashAttribute("erroMensagem", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erroMensagem", "Erro interno ao excluir o encontro.");
        }
        
        return "redirect:/lista-encontros";
    }
}
