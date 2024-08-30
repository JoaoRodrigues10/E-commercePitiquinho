package br.loja.pitiquinho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.UsuarioService;

@Controller
@RequestMapping("/criar-conta")
public class CriarContaController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "criar-conta";
    }

    @PostMapping
    public String registerUser(Usuario usuario) {
        // Criptografe a senha antes de salvar
        usuario.setSenha(usuarioService.encodePassword(usuario.getSenha()));
        usuarioService.save(usuario);
        return "redirect:/login"; // 
    }
}
