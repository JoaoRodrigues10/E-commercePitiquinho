package br.loja.pitiquinho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.loja.pitiquinho.service.UsuarioService;

@Controller
public class ListarUsuariosBackoffice {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar-usuario")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodosUsuarios());
        return "listar-usuario";
    }

 
    
}
