package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Cliente;
import br.loja.pitiquinho.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cadastro-cliente"; // Nome da view de cadastro
    }

    @PostMapping("/cadastro")
    public String cadastrarCliente(@Valid Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Retorna ao formulário com os erros
            model.addAttribute("cliente", cliente);
            return "cadastro-cliente";
        }

        // Verifica se o email já existe
        if (clienteService.emailExistente(cliente.getEmail())) {
            model.addAttribute("error", "Email já cadastrado.");
            return "cadastro-cliente";
        }

        // Verifica se o CPF já existe
        if (clienteService.cpfExistente(cliente.getCpf())) {
            model.addAttribute("error", "CPF já cadastrado.");
            return "cadastro-cliente";
        }

        // Criptografar a senha antes de salvar
        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));

        // Salvar o cliente
        clienteService.salvarCliente(cliente);
        return "redirect:/login"; // Redireciona para a página de login
    }
}
