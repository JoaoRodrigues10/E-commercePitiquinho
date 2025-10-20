package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.repository.UsuarioRepository;
import br.loja.pitiquinho.util.util;

@Controller
public class AlterarUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private util util;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @RequestMapping("/alterar-usuario")
    public String alterarUsuario(@RequestParam Long id, @RequestParam String nome, @RequestParam String email, @RequestParam String cpf, @RequestParam String grupo, @RequestParam(required = false) String senha, @RequestParam(required = false) String confirmarSenha, Model model) {


        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        usuario.setNome(nome);
        String cpfLimpo = cpf.replaceAll("[^\\d]", "");
        usuario.setCpf(cpfLimpo);
        usuario.setGrupo(grupo);

        if (!util.validarCPF(cpf)) {

            model.addAttribute("error", "CPF inválido");
            return "redirect:/lista-usuario";
        }

        if (senha != null && !senha.isEmpty() && senha != "") {
            if (!senha.equals(confirmarSenha)) {
                model.addAttribute("error", "As senhas não correspondem");
                return "redirect:/lista-usuario";
            }
            usuario.setSenha(passwordEncoder.encode(senha));
        }


        usuarioRepository.save(usuario);

        return "redirect:/lista-usuario";
    }


    @PostMapping("/alterar-usuario/desativar")
    public String desativarUsuario(@RequestParam Long id, @RequestParam boolean currentStatus, RedirectAttributes redirectAttributes) {

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setStatus(!currentStatus);

        usuarioRepository.save(usuario);

        String mensagem = currentStatus ? "Usuário desativado com sucesso" : "Usuário ativado com sucesso";
        redirectAttributes.addFlashAttribute("success", mensagem);

        return "redirect:/lista-usuario";
    }



}
