package br.loja.pitiquinho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BackofficeController {
    
    @GetMapping("/backoffice")
    public String backoffice() {
        return "backoffice"; 
    }

    
}
