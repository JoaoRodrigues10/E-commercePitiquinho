package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.EnderecoEntrega;
import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.repository.UsuarioRepository;
import br.loja.pitiquinho.service.EnderecoEntregaService;
import br.loja.pitiquinho.service.UsuarioService;
import br.loja.pitiquinho.util.util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioClienteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoEntregaService enderecoEntregaService;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private boolean isNomeValido(String nome) {
        String[] partes = nome.split(" ");
        if (partes.length < 2) return false; // Verifica se há pelo menos 2 palavras
        for (String parte : partes) {
            if (parte.length() < 3) return false; // Verifica se cada palavra tem pelo menos 3 letras
        }
        return true;
    }


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

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id).orElse(null);
        if (usuario == null) {
            return "redirect:/"; // Redireciona se o usuário não for encontrado
        }
        model.addAttribute("usuario", usuario);

        List<EnderecoEntrega> enderecos = enderecoEntregaService.buscarEnderecosPorUsuarioId(usuario.getId());
        model.addAttribute("enderecos", enderecos);



        return "editar-usuario";
    }


    @PostMapping("/editar/{id}")
    public String alterarUsuario(@PathVariable Long id, @Valid @ModelAttribute("usuario") Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "editar-usuario";  // Retorna se houver erros de validação
        }


        Usuario usuarioExistente = usuarioService.buscarUsuarioPorId(id).orElse(null);
        if (usuarioExistente == null) {
            return "redirect:/usuario/listar";
        }

        EnderecoEntrega novoEndereco = new EnderecoEntrega();




            String nome = usuario.getNome();
            if (nome == null || nome.isEmpty() || !isNomeValido(nome)) {
                result.rejectValue("nome", "error.usuario", "O nome deve ter pelo menos duas palavras, com no mínimo 3 letras em cada uma.");
                return "editar-usuario";
            }





        String cep = usuario.getCepFaturamento();
        if (cep == null || cep.isEmpty()) {
            result.rejectValue("cepFaturamento", "error.usuario", "CEP não pode ser nulo ou vazio.");
            return "editar-usuario";
        }

        cep = cep.replace("-", "");
        ResponseEntity<String> response = buscarEndereco(cep);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            result.rejectValue("cepFaturamento", "error.usuario", "CEP inválido. Por favor, verifique o CEP.");
            return "editar-usuario";
        }


        usuarioExistente.setNome(usuario.getNome());


        novoEndereco.setCep(usuario.getCepFaturamento());
        novoEndereco.setLogradouro(usuario.getLogradouroFaturamento());
        novoEndereco.setNumero(usuario.getNumeroFaturamento());
        novoEndereco.setComplemento(usuario.getComplementoFaturamento());
        novoEndereco.setBairro(usuario.getBairroFaturamento());
        novoEndereco.setCidade(usuario.getCidadeFaturamento());
        novoEndereco.setUf(usuario.getUfFaturamento());
        novoEndereco.setUsuario(usuarioExistente);

        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        enderecoEntregaService.salvarEndereco(novoEndereco);
        usuarioService.atualizarUsuario(usuarioExistente.getId(), usuarioExistente);
        return "redirect:/"; // Redireciona após a atualização
    }



    @GetMapping("/enderecos")
    public String listarEnderecos(Model model) {
        List<EnderecoEntrega> enderecos = enderecoEntregaService.buscarEnderecosPorUsuarioId(6L);
        model.addAttribute("enderecos", enderecos);
        return "enderecos";
    }

    @GetMapping("/excluir-endereco/{id}")
    public String excluirEndereco(@PathVariable Long id) {
        enderecoEntregaService.excluirEndereco(id);
        return "redirect:/";
    }

}
