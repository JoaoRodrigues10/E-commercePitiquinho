package br.loja.pitiquinho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.loja.pitiquinho.service.UsuarioService;
import br.loja.pitiquinho.model.Usuario;

import java.util.List;

@Controller
public class ListarUsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/adm/lista-usuario")
    public String listaUsuario(@RequestParam(value = "nome", defaultValue = "") String nome, Model model) {

        List<Usuario> usuarios;


        System.out.println(nome);

        if (nome.isEmpty()) {
            usuarios = usuarioService.listarTodosUsuarios();
        } else {
            usuarios = usuarioService.buscarUsuariosPorNome(nome);
        }
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("nome", nome);


        return "lista-usuario-adm";
    }
}
