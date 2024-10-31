package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Endereco;
import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.EnderecoService;
import br.loja.pitiquinho.service.UsuarioService;
import br.loja.pitiquinho.util.util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/usuario")
public class UsuarioClienteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/buscar-endereco")
    @ResponseBody
    public ResponseEntity<String> buscarEndereco(@RequestParam String cep) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            return ResponseEntity.ok(restTemplate.getForObject(url, String.class));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"Erro ao buscar endereço.\"}");
        }
    }

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro-usuario";
    }


    @PostMapping("/cadastro")
    public String cadastrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult result, Model model,HttpSession session) {


        if (result.hasErrors() || !validarCadastro(usuario, result)) {
            return "cadastro-usuario";
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setGrupo("Cliente");
        usuarioService.criarUsuario(usuario);

        model.addAttribute("usuario",  usuario);
        session.setAttribute("usuario", usuario);

        return "redirect:/usuario/endereco?usuarioId=" + usuario.getId();

    }


    @GetMapping("/endereco")
    public String mostrarCadastroEndereco(@RequestParam("usuarioId") Long usuarioId, Model model,HttpSession session) {
        Usuario usuario = usuarioService.findById(usuarioId);
        List<Endereco> enderecos = enderecoService.buscarEnderecosPorUsuarioId(usuarioId);
        model.addAttribute("usuario", usuario);
        model.addAttribute("enderecos", enderecos);
        model.addAttribute("endereco", new Endereco());

        return "endereco";
    }

    @PostMapping("/cadastro-endereco")
    public String cadastrarEndereco(@Valid @ModelAttribute("endereco") Endereco endereco,
        BindingResult result, HttpSession session, Model model) {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");


        if (usuarioLogado == null || usuarioLogado.getGrupo() == null || usuarioLogado.getGrupo().isEmpty()) {
            return "redirect:/login";
        }


        endereco.setUsuarioId(usuarioLogado.getId());

        if (result.hasErrors()) {
            return "redirect:/usuario/endereco?usuarioId=" + usuarioLogado.getId();
        }

        enderecoService.salvarEndereco(endereco);

        //return "redirect:/usuario/editar/" + usuarioLogado.getId();

        return "redirect:/";
    }

    @GetMapping("/enderecos/{usuarioId}")
    public String listarEnderecos(@PathVariable Long usuarioId, Model model, HttpSession session) {
        Usuario usuario = usuarioService.findById(usuarioId);
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        if (usuarioLogado == null || usuarioLogado.getGrupo() == null || usuarioLogado.getGrupo().isEmpty()) {
            return "redirect:/login";
        }

        List<Endereco> enderecos = enderecoService.buscarEnderecosPorUsuarioId(usuarioLogado.getId());


        boolean temEnderecoFaturamento = enderecos.stream()
                .anyMatch(endereco -> "Faturamento".equals(endereco.getTipoEndereco()));

        model.addAttribute("usuario", usuario);
        model.addAttribute("enderecos", enderecos);
        model.addAttribute("endereco", new Endereco());
        model.addAttribute("temEnderecoFaturamento", temEnderecoFaturamento);

        return "endereco";
    }



    @GetMapping("/excluir-endereco/{id}")
    public String excluirEndereco(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Endereco endereco = enderecoService.findById(id);

        if ("Faturamento".equalsIgnoreCase(endereco.getTipoEndereco())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Não é permitido apagar o endereço de faturamento.");
            return "redirect:/enderecos"; // redireciona para a página de endereços
        }

        enderecoService.excluirEndereco(id);
        redirectAttributes.addFlashAttribute("successMessage", "Endereço excluído com sucesso.");
        return "redirect:/enderecos"; // redireciona para a página de endereços
    }





    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao( Model model,HttpSession session) {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        model.addAttribute("usuario", usuarioLogado);
        session.setAttribute("usuario", usuarioLogado);

        if (usuarioLogado == null || usuarioLogado.getGrupo() == null || usuarioLogado.getGrupo().isEmpty()) {
            return "redirect:/login";
        }

        System.out.println(usuarioLogado.getId());
        return "editar-usuario";
    }


    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, @Valid @ModelAttribute("usuario") Usuario usuario,
        BindingResult result, Model model, HttpSession session) {

        if (result.hasErrors()) {
            return "editar-usuario";
        }

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        if (usuarioLogado == null || usuarioLogado.getGrupo() == null || usuarioLogado.getGrupo().isEmpty()) {
            return "redirect:/login";
        }

        if (!usuario.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        } else {
            usuario.setSenha(usuarioLogado.getSenha());
        }

        usuario.setId(id);
        usuario.setGrupo(usuarioLogado.getGrupo());
        usuarioService.atualizarUsuario(usuario);

        model.addAttribute("usuario", usuario);
        session.setAttribute("usuario", usuario);

        return "redirect:/usuario/detalhes/" + usuario.getId();
    }


    private boolean validarCadastro(Usuario usuario, BindingResult result) {
        if (usuarioService.existsByEmail(usuario.getEmail())) {
            result.rejectValue("email", "error.usuario", "Email já cadastrado");
            return false;
        }
        if (usuarioService.existsByCpf(usuario.getCpf())) {
            result.rejectValue("cpf", "error.usuario", "CPF já cadastrado");
            return false;
        }
        if (!util.validarCPF(usuario.getCpf())) {
            result.rejectValue("cpf", "error.usuario", "CPF inválido");
            return false;
        }
        return true;
    }

    private boolean isNomeValido(String nome) {
        String[] partes = nome.split(" ");
        return partes.length >= 2 && partes.length > 0 && partes[0].length() >= 3;
    }

}

