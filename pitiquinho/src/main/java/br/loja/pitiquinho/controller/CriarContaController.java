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

    private boolean validarCPF(String cpf2) {
        // Remove todos os caracteres não numéricos
        String cpf = cpf2.replaceAll("\\D", "");
    
        // Verifica se o CPF tem 11 dígitos e não é uma sequência repetitiva de dígitos
        if (cpf.length() != 11 || cpf.chars().allMatch(c -> c == cpf.charAt(0))) {
            return false;
        }
    
        int soma = 0;
        int resto;
    
        // Calcula o primeiro dígito verificador
        for (int i = 1; i <= 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i - 1)) * (11 - i);
        }
        resto = (soma * 10) % 11;
        if (resto == 10 || resto == 11) resto = 0;
        if (resto != Character.getNumericValue(cpf.charAt(9))) return false;
    
        soma = 0;
    
        // Calcula o segundo dígito verificador
        for (int i = 1; i <= 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i - 1)) * (12 - i);
        }
        resto = (soma * 10) % 11;
        if (resto == 10 || resto == 11) resto = 0;
        return resto == Character.getNumericValue(cpf.charAt(10));
    }

    private boolean senhaConfirmada(String senha, String confirmarSenha) {
        return senha != null && senha.equals(confirmarSenha);
    }

    @PostMapping("/criar-conta")
    public String CriarConta(@ModelAttribute @Valid Usuario usuario, @RequestParam String confirmarSenha,BindingResult bindingResult, Model model) {

        
        if (bindingResult.hasErrors()) {
            return "register"; 
        }


        if (!validarCPF(usuario.getCpf())) {
            bindingResult.rejectValue("cpf", "error.usuario", "CPF inválido");
            return "register"; 
        }


        if (!senhaConfirmada(usuario.getSenha(), confirmarSenha)) {
            bindingResult.rejectValue("confirmarSenha", "error.usuario", "As senhas não correspondem");
            return "register"; 
        }


        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);

        return "redirect:/login";
    }

}

