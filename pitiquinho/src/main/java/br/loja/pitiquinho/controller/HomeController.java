package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        if (usuarioLogado == null || usuarioLogado.getGrupo() == null || usuarioLogado.getGrupo().isEmpty()) {
            return "redirect:/";
        }

        return "backoffice";
    }
}
