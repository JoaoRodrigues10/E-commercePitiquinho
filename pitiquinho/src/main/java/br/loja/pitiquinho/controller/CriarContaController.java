package br.loja.pitiquinho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.util.util;
import br.loja.pitiquinho.repository.UsuarioRepository;
import br.loja.pitiquinho.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/criar-conta")
public class CriarContaController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private util util;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "criar-conta";
    }

    @GetMapping("/lista-adm")
    public String listarTelas(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "lista-adm";
    }

    @PostMapping
    public String CriarConta(@ModelAttribute @Valid Usuario usuario, @RequestParam String confirmarSenha,BindingResult bindingResult, Model model) {

        
        if (bindingResult.hasErrors()) {
            return "criar-conta"; 
        }


        if (!util.validarCPF(usuario.getCpf())) {
            bindingResult.rejectValue("cpf", "error.usuario", "CPF inválido");
            return "criar-conta"; 
        }


        if (!util.senhaConfirmada(usuario.getSenha(), confirmarSenha)) {
            bindingResult.rejectValue("confirmarSenha", "error.usuario", "As senhas não correspondem");
            return "criar-conta"; 
        }


        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);

        return "redirect:/login";
    }

}

