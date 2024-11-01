package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Endereco;
import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.EnderecoService;
import br.loja.pitiquinho.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private UsuarioService usuarioService;

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
    public String mostrarCadastroEndereco(@RequestParam("usuarioId") Long usuarioId, Model model, HttpSession session) {
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
           return "redirect:/endereco/cadastro?usuarioId=" + usuarioLogado.getId();
        }

        enderecoService.salvarEndereco(endereco);

        return "redirect:/endereco/editar/" + usuarioLogado.getId();
    }

    @GetMapping("/cadastro-faturamento")
    public String mostrarCadastroEnderecoFaturamento(@RequestParam("usuarioId") Long usuarioId, Model model, HttpSession session) {
        Usuario usuario = usuarioService.findById(usuarioId);
        model.addAttribute("usuario", usuario);
        model.addAttribute("endereco", new Endereco());
        return "endereco-faturamento";
    }

    @PostMapping("/cadastro-endereco-faturamento")
    public String cadastrarEnderecoFaturamento(@Valid @ModelAttribute("endereco") Endereco endereco,
                                    BindingResult result, HttpSession session, Model model) {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        if (usuarioLogado == null || usuarioLogado.getGrupo() == null || usuarioLogado.getGrupo().isEmpty()) {
            return "redirect:/login";
        }

        endereco.setUsuarioId(usuarioLogado.getId());

        if (result.hasErrors()) {
            return "redirect:/endereco/cadastro?usuarioId=" + usuarioLogado.getId();
        }

        enderecoService.salvarEndereco(endereco);

        //return "redirect:/endereco/editar/" + usuarioLogado.getId();
        return "redirect:/carrinho";
    }

    @GetMapping("/editar/{usuarioId}")
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

    @GetMapping("/excluir/{id}")
    public String excluirEndereco(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpSession session) {
        Endereco endereco = enderecoService.findById(id);

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
        if (usuarioLogado == null) {
            return "redirect:/login";
        }

        if ("Faturamento".equalsIgnoreCase(endereco.getTipoEndereco())) {
            return "redirect:/endereco/editar/" + usuarioLogado.getId();
        }

        enderecoService.excluirEndereco(id);
        redirectAttributes.addFlashAttribute("successMessage", "Endereço excluído com sucesso.");
        return "redirect:/endereco/editar/" + usuarioLogado.getId();
    }

}
