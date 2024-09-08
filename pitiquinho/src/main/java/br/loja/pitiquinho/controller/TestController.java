package br.loja.pitiquinho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/teste")
    public String showTestPage() {
        return "teste"; 
    }
}
