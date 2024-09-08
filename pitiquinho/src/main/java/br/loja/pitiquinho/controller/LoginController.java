package br.loja.pitiquinho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.loja.pitiquinho.config.JwtUtil;
import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.UsuarioService;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService; 
    
    @GetMapping("/adm/login")
    public String LoginPage(Model model) {
        return "login-adm"; 
    }


    @GetMapping("/adm/lista-adm")
    public String home(Model model) {
        return "lista-adm";
    }

    @PostMapping("/adm/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/adm/login";
    }

    @PostMapping("/adm/login-adm")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Usuario usuario = usuarioService.buscarLogin(email, password);
    
        if (usuario != null) {
            String token = JwtUtil.generateToken(usuario);

            session.setAttribute("usuario", usuario);
            session.setAttribute("token", token);

            return "redirect:/adm/lista-adm";
        } else {
            return "redirect:/adm/login?error";
        }
    }
    

}
