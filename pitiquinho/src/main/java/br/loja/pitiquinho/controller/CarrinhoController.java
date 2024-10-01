package br.loja.pitiquinho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CarrinhoController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/carrinho")
    public String carrinhoPage(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        return "carrinho";
    }

    @RequestMapping("/buscar-endereco")
    @ResponseBody
    public String buscarEndereco(@RequestParam String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        String response = restTemplate.getForObject(url, String.class);
        return response; // Retorna o JSON do endereço
    }
}
