package br.loja.pitiquinho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.repository.UsuarioRepository;
import br.loja.pitiquinho.service.UsuarioService;

@Controller
@RequestMapping("/criar-conta")
public class CriarContaController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public String registerUser(Usuario usuario,Model model) {

        usuario.setSenha(usuarioService.encodePassword(usuario.getSenha()));

        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            model.addAttribute("error", "CPF já cadastrado.");
            model.addAttribute("usuario", usuario);
            return "criar-conta";
        }
    
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            model.addAttribute("error", "Email já cadastrado.");
            model.addAttribute("usuario", usuario);
         
         
           return "criar-conta";
        }
    
        try {
            usuarioRepository.save(usuario);
            return "redirect:/lista-adm";
        } catch (Exception ex) {
            model.addAttribute("error", "Ocorreu um erro inesperado.");
            model.addAttribute("usuario", usuario);
            return "criar-conta";
        }
  
    }
}
