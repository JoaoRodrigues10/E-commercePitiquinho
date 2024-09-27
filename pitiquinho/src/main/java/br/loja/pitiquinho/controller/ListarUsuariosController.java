package br.loja.pitiquinho.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.loja.pitiquinho.service.UsuarioService;
import br.loja.pitiquinho.model.Usuario;

import java.util.List;
import jakarta.servlet.http.HttpSession;

@Controller
public class ListarUsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/lista-usuario")
    public String listaUsuario(@RequestParam(value = "nome", defaultValue = "") String nome, Model model, HttpSession session) {

        List<Usuario> usuarios;

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioLogado);

        if (usuarioLogado == null || usuarioLogado.getGrupo() == null || usuarioLogado.getGrupo().isEmpty() || usuarioLogado.getGrupo().equals("Estoquista")) {

            if(usuarioLogado.getGrupo().equals("Estoquista")){
                return "redirect:/home";
            } else {
                return "redirect:/";
            }
        }


        System.out.println(nome);

        if (nome.isEmpty()) {
            usuarios = usuarioService.listarTodosUsuarios();
        } else {
            usuarios = usuarioService.buscarUsuariosPorNome(nome);
        }
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("nome", nome);
        boolean algumDesativado = usuarios.stream().anyMatch(u -> "Desativado".equals(u.getStatus()));
        model.addAttribute("algumDesativado", algumDesativado);


        return "lista-usuario";
    }
}
