package br.loja.pitiquinho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.loja.pitiquinho.service.UsuarioService;
import br.loja.pitiquinho.model.Usuario;

import java.util.List;

@Controller
public class ListarUsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/adm/lista-usuario")
    public String listaUsuario(Model model) {

        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        model.addAttribute("usuarios", usuarios);

        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId() +
                    ", Nome: " + usuario.getNome() +
                    ", E-mail: " + usuario.getEmail() +
                    ", Status: " + usuario.getStatus() +
                    ", Grupo: " + usuario.getGrupo());
        }

        return "lista-usuario-adm";
    }



}
