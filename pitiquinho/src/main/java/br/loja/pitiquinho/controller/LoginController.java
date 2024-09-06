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
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return "redirect:/login?error=emptyFields";
        }
    

        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (usuario != null && usuarioService.verificarSenha(usuario, password)) {
            // Validar CPF
            if (!validarCPF(usuario.getCpf())) {
                return "redirect:/login?error=invalidCPF";
            }
    

            if (!senhaConfirmada(usuario.getSenha(), password)) {
                return "redirect:/login?error=passwordMismatch";
            }
    
          
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
    

            session = request.getSession(true);
            session.setAttribute("usuario", usuario);
    

            if (usuario.getGrupo().equals("Administrador")) {
                return "redirect:/lista-adm";
            } else {
                return "redirect:/lista-estoque";
            }
        } else {

            return "redirect:/login?error=invalidCredentials";
        }
    }
    

    private boolean validarCPF(String Usuariocpf) {
        String cpf = Usuariocpf.replaceAll("\\D", ""); 
    
        if (cpf.length() != 11 || cpf.chars().allMatch(c -> c == cpf.charAt(0))) {
            return false;
        }
    
        int soma = 0;
        int resto;
    
        for (int i = 1; i <= 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i - 1)) * (11 - i);
        }
        resto = (soma * 10) % 11;
        if (resto == 10 || resto == 11) resto = 0;
        if (resto != Character.getNumericValue(cpf.charAt(9))) return false;
    
        soma = 0;
        for (int i = 1; i <= 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i - 1)) * (12 - i);
        }
        resto = (soma * 10) % 11;
        if (resto == 10 || resto == 11) resto = 0;
        return resto == Character.getNumericValue(cpf.charAt(10));
    }


    private boolean senhaConfirmada(String senha, String senhaConfirmada) {
        return senha.equals(senhaConfirmada);
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/login";
    }


}
