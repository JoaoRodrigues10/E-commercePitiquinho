package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.loja.pitiquinho.util.util;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/usuario")
public class UsuarioClienteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro-usuario";
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "cadastro-usuario";
        }

        usuario.setGrupo("Usuario");

        if (usuarioService.existsByEmail(usuario.getEmail())) {
            result.rejectValue("email", "error.usuario", "Email já cadastrado");
            return "cadastro-usuario";
        }

        if (usuarioService.existsByCpf(usuario.getCpf())) {
            result.rejectValue("cpf", "error.usuario", "CPF inválido");
            return "cadastro-usuario";
        }

        if (!util.validarCPF(usuario.getCpf())) {
            model.addAttribute("cpf", "CPF inválido");
            return "cadastro-usuario";
        }


        // Codifica a senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioService.criarUsuario(usuario);
        return "redirect:/login";
    }
}
