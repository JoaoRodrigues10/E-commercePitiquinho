package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.UsuarioService;
import br.loja.pitiquinho.util.util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/usuario")
public class UsuarioClienteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @RequestMapping("/buscar-endereco")
    @ResponseBody
    public ResponseEntity<String> buscarEndereco(@RequestParam String cep) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            String response = restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Retorna um erro caso a consulta falhe
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao buscar endereço.\"}");
        }
    }

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro-usuario";
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cadastro-usuario";  // Retorne imediatamente se houver erros de validação
        }

        usuario.setGrupo("Usuario");

        if (usuarioService.existsByEmail(usuario.getEmail())) {
            result.rejectValue("email", "error.usuario", "Email já cadastrado");
            return "cadastro-usuario";
        }

        if (usuarioService.existsByCpf(usuario.getCpf())) {
            result.rejectValue("cpf", "error.usuario", "CPF inválido");  // Usando rejectValue
            return "cadastro-usuario";
        }

        if (!util.validarCPF(usuario.getCpf())) {
            result.rejectValue("cpf", "error.usuario", "CPF inválido");  // Usando rejectValue
            return "cadastro-usuario";
        }

        // Verifique se o cepFaturamento é nulo antes de usar
        String cep = usuario.getCepFaturamento();
        if (cep == null || cep.isEmpty()) {  // Verifica se é nulo ou vazio
            result.rejectValue("cepFaturamento", "error.usuario", "CEP não pode ser nulo ou vazio.");
            return "cadastro-usuario";
        }

        cep = cep.replace("-", "");

        ResponseEntity<String> response = buscarEndereco(cep);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            result.rejectValue("cepFaturamento", "error.usuario", "CEP inválido. Por favor, verifique o CEP.");  // Usando rejectValue
            return "cadastro-usuario";
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioService.criarUsuario(usuario);
        return "redirect:/login";
    }

}
