package br.loja.pitiquinho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;


@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService; 
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        Usuario usuario = usuarioService.buscarLogin(username, password);

        if (usuario != null) {

            if(usuario.getGrupo().equals("Administrador")){
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                return "redirect:/lista-adm";
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                return "redirect:/lista-estoque";
            }
        } else {
            return "redirect:/login?error";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login";
    }


}
