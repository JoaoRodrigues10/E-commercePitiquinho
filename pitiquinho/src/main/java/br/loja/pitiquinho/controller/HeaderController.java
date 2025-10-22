package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Endereco;
import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.EnderecoService;
import br.loja.pitiquinho.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class HeaderController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private UsuarioService usuarioService;

    @ModelAttribute
    public void adicionarDadosHeader(Model model,
                                     @SessionAttribute(name="usuario", required=false) Usuario usuarioSessao) {
        if (usuarioSessao != null) {

            Usuario usuarioAtualizado = usuarioService.findById(usuarioSessao.getId());
            model.addAttribute("usuario", usuarioAtualizado);

            Endereco endereco = enderecoService.buscarEnderecoPadrao(usuarioAtualizado.getId());
            model.addAttribute("enderecoUsuario", endereco);
        }
    }
}
