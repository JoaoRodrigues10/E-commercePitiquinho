package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.UsuarioService;
import br.loja.pitiquinho.util.util;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public String cadastrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result, Model model, HttpSession session) {

        if (result.hasErrors() || !validarCadastro(usuario, result)) {
            return "cadastro-usuario";
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setGrupo("Cliente");
        usuarioService.criarUsuario(usuario);

        model.addAttribute("usuario", usuario);
        session.setAttribute("usuario", usuario);

        return "redirect:/endereco/cadastro-faturamento?usuarioId=" + usuario.getId();
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        if (usuarioLogado == null || usuarioLogado.getGrupo() == null || usuarioLogado.getGrupo().isEmpty()) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuarioLogado);
        session.setAttribute("usuario", usuarioLogado);

        return "editar-usuario";
    }

    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, @Valid @ModelAttribute("usuario") Usuario usuario,
                                BindingResult result, Model model, HttpSession session) {

        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "editar-usuario";
        }


        if (!validarCadastro2(usuario, result)) {
            model.addAttribute("usuario", usuario);
            return "editar-usuario"; // Retorna à página de edição se houver erros de validação
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        if (usuarioLogado == null || usuarioLogado.getGrupo() == null || usuarioLogado.getGrupo().isEmpty()) {
            return "redirect:/login";
        }


        if (!usuario.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        } else {
            usuario.setSenha(usuarioLogado.getSenha());
        }

        usuario.setId(id);
        usuario.setGrupo(usuarioLogado.getGrupo());
        usuarioService.atualizarUsuario(usuario);


        session.setAttribute("usuario", usuario);
        return "redirect:/usuario/editar/" + usuario.getId();
    }

    private boolean validarCadastro(Usuario usuario, BindingResult result) {
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            result.rejectValue("email", "error.usuario", "Email já cadastrado");
            return false;
        }
        if (usuarioService.existsByCpf(usuario.getCpf())) {
            result.rejectValue("cpf", "error.usuario", "CPF já cadastrado");
            return false;
        }
        if (!util.validarCPF(usuario.getCpf())) {
            result.rejectValue("cpf", "error.usuario", "CPF inválido");
            return false;
        }
        return true;
    }

    private boolean validarCadastro2(Usuario usuario, BindingResult result) {

        if (usuarioService.existsByCpf(usuario.getCpf())) {
            result.rejectValue("cpf", "error.usuario", "CPF já cadastrado");
            return false;
        }
        if (!util.validarCPF(usuario.getCpf())) {
            result.rejectValue("cpf", "error.usuario", "CPF inválido");
            return false;
        }
        return true;
    }
}