package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.repository.UsuarioRepository;
import br.loja.pitiquinho.util.util;

@Controller
public class AdicionarUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private util util;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/adicionar-usuario")
    public String mostrarFormularioAdicionarUsuario(@RequestParam(value = "error", required = false) String error,
                                                    @RequestParam(value = "success", required = false) String success,
                                                    Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        if (success != null) {
            model.addAttribute("successMessage", success);
        }

        if (model.containsAttribute("nome")) {
            model.addAttribute("nome", model.asMap().get("nome"));
        }
        if (model.containsAttribute("email")) {
            model.addAttribute("email", model.asMap().get("email"));
        }
        if (model.containsAttribute("cpf")) {
            model.addAttribute("cpf", model.asMap().get("cpf"));
        }
        if (model.containsAttribute("grupo")) {
            model.addAttribute("grupo", model.asMap().get("grupo"));
        }
        return "adicionar-usuario";
    }


    @PostMapping("/adicionar-usuario")
    public String adicionarUsuario(@RequestParam String nome, @RequestParam String email, @RequestParam String cpf,
                                   @RequestParam String grupo, @RequestParam String senha,
                                   @RequestParam String confirmarSenha, RedirectAttributes redirectAttributes, Model model) {

        if (usuarioRepository.findByEmail(email) != null) {

            redirectAttributes.addFlashAttribute("nome", nome);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("cpf", cpf);
            redirectAttributes.addFlashAttribute("grupo", grupo);
            redirectAttributes.addFlashAttribute("errorMessage", "O e-mail já está em uso");
            return "redirect:/adicionar-usuario";
        }

        if (!senha.equals(confirmarSenha)) {

            redirectAttributes.addFlashAttribute("nome", nome);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("cpf", cpf);
            redirectAttributes.addFlashAttribute("grupo", grupo);
            redirectAttributes.addFlashAttribute("errorMessage", "As senhas não correspondem");

            return "redirect:/adicionar-usuario";
        }

        if (!util.validarCPF(cpf)) {

            redirectAttributes.addFlashAttribute("nome", nome);
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("cpf", cpf);
            redirectAttributes.addFlashAttribute("grupo", grupo);
            redirectAttributes.addFlashAttribute("errorMessage", "CPF inválido");

            return "redirect:/adicionar-usuario";
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);

        String cpfLimpo = cpf.replaceAll("[^\\d]", "");
        usuario.setCpf(cpfLimpo);

        usuario.setGrupo(grupo);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setStatus(true);

        usuarioRepository.save(usuario);

        redirectAttributes.addFlashAttribute("success", "Usuário adicionado com sucesso");
        return "redirect:/lista-usuario";
    }

}



