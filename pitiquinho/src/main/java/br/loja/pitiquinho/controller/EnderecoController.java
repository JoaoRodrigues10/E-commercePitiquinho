package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Endereco;
import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.repository.EnderecoRepository;
import br.loja.pitiquinho.service.EnderecoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    // Lista os endereços do usuário
    @GetMapping("/listar")
    public String listarEnderecos(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<Endereco> enderecos = enderecoRepository.findByUsuarioId(usuario.getId());
        enderecos.sort(Comparator.comparing(Endereco::isPadrao).reversed());
        model.addAttribute("enderecos", enderecos);

        model.addAttribute("novoEndereco", new Endereco());
        model.addAttribute("usuario", usuario);
        return "endereco"; // nome do Thymeleaf
    }

    @PostMapping("/salvar")
    public String salvarEndereco(HttpSession session, @ModelAttribute Endereco endereco) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        boolean primeiroEndereco = enderecoRepository.countByUsuarioId(endereco.getUsuarioId()) == 0;

        if (primeiroEndereco) {
            // Primeiro endereço do usuário já é padrão
            endereco.setPadrao(true);
        } else {
            // Qualquer outro endereço não pode ser padrão na criação
            endereco.setPadrao(false);
        }

        endereco.setUsuarioId(usuario.getId()); // setar o objeto completo
        enderecoService.salvarEndereco(endereco);
        return "redirect:/endereco/listar";
    }


    // Definir endereço padrão
    @GetMapping("/definir-padrao/{id}")
    public String definirPadrao(HttpSession session, @PathVariable Long id) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        enderecoService.definirPadrao(id, usuario.getId());
        return "redirect:/endereco/listar";
    }

    // Excluir endereço
    @GetMapping("/excluir/{id}")
    public String excluirEndereco(HttpSession session, @PathVariable Long id) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Endereco e = enderecoService.buscarEnderecoPorId(id);
        if (e.getUsuario().getId().equals(usuario.getId())) { // segurança: só pode excluir o próprio
            enderecoService.excluirEndereco(id);
        }
        return "redirect:/endereco/listar";
    }

    // Editar: apenas retorna o mesmo modal com o objeto preenchido
    @GetMapping("/editar/{id}")
    public String editarEndereco(HttpSession session, @PathVariable Long id, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Endereco endereco = enderecoService.buscarEnderecoPorId(id);
        if (!endereco.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/endereco/listar"; // segurança
        }
        model.addAttribute("novoEndereco", endereco);
        model.addAttribute("enderecos", enderecoService.buscarEnderecosPorUsuarioId(usuario.getId()));
        model.addAttribute("usuario", usuario);
        return "enderecos";
    }
}
