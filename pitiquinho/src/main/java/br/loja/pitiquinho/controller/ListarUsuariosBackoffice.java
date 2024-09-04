package br.loja.pitiquinho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.UsuarioService;




@Controller
@RequestMapping("/listar-usuario")
public class ListarUsuariosBackoffice {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listarusuariobackoffice")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodosUsuarios());
        return "listarusuariobackoffice";
    }

    @PostMapping("/atualizar")
    public String atualizarUsuario(
            @RequestParam Long id,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam Boolean status,
            @RequestParam String grupo) {

            usuarioService.atualizarUsuario(id, nome, email, status, grupo);
            return "redirect:/listar-usuario/listarusuariobackoffice";
    }

 
    
}
