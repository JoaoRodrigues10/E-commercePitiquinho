package br.loja.pitiquinho.config;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalAttributes {

    @ModelAttribute("usuario")
    public Object addUsuario(HttpSession session) {
        return session.getAttribute("usuario");
    }

    @ModelAttribute("enderecoUsuario")
    public Object addEnderecoUsuario(HttpSession session) {
        return session.getAttribute("enderecoUsuario");
    }
}
