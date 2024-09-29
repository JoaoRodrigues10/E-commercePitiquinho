package br.loja.pitiquinho.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicialController {

    @GetMapping("/")
    public String home() {
        return "produtos";
    }
}