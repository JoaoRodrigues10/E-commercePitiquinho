package br.loja.pitiquinho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/lista-adm")
    public String home() {
        return "/lista-adm"; 
    }

    @GetMapping("/lista-estoque")
    public String home2() {
        return "/lista-estoque"; 
    }
}
