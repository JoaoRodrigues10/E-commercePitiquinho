package br.loja.pitiquinho.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdmController {

    @GetMapping("/adm/home")
    public String home(Model model) {
        return "home-adm";
    }


}
