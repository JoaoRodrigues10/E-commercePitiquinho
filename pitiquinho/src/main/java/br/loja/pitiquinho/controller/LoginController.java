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

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "redirect", required = false) String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }



    @PostMapping("/logout")
    public String logoutGet(HttpSession session) {
        session.invalidate();
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam(value = "redirect", required = false) String redirect,
                        HttpSession session) {
        Usuario usuario = usuarioService.buscarLogin(email, password);

        if (usuario != null) {
            String token = JwtUtil.generateToken(usuario);
            session.setAttribute("usuario", usuario);
            session.setAttribute("token", token);


            if (redirect != null && !redirect.isEmpty()) {
                return "redirect:/" + redirect;
            }

            if (usuario.getGrupo().equals("Cliente")) {
                return "redirect:/";
            } else {
                return "redirect:/backoffice";
            }

        } else {
            return "redirect:/login?error";
        }
    }


}
