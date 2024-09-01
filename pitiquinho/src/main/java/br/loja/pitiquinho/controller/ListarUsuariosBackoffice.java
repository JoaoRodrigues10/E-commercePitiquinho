package br.loja.pitiquinho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import br.loja.pitiquinho.service.UsuarioService;
import br.loja.pitiquinho.model.Usuario;

@Controller
public class ListarUsuariosBackoffice {

    @Autowired
    private UsuarioService usuario;


    @GetMapping("/listarusuariobackoffice")
    public String listarusuariobackoffice (){
        //List<Usuario> = usuario.listarUsuarios();

        return "listarusuariobackoffice";
    }

    
}
