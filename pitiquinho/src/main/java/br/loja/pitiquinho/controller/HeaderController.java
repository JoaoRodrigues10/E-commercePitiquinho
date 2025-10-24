package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Endereco;
import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.EnderecoService;
import br.loja.pitiquinho.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Optional;

@ControllerAdvice
public class HeaderController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private UsuarioService usuarioService;

    @ModelAttribute
    public void adicionarDadosHeader(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        if (usuario != null) {
            // Busca endereço padrão, que pode ser null
            Endereco enderecoPadrao = enderecoService.buscarEnderecoPadrao(usuario.getId());
            model.addAttribute("enderecoUsuario", enderecoPadrao); // será null se não existir
        } else {
            model.addAttribute("enderecoUsuario", null);
        }
    }


}
