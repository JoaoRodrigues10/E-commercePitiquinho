package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Pedido;
import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.EnderecoEntregaService;
import br.loja.pitiquinho.service.PedidoService;
import br.loja.pitiquinho.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckoutController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoService pedidoService;


    @Autowired
    private EnderecoEntregaService enderecoEntregaService;

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");

        if (usuarioLogado == null || usuarioLogado.getGrupo() == null || usuarioLogado.getGrupo().isEmpty()) {
            return "redirect:/login";
        }

        model.addAttribute("enderecos", enderecoEntregaService. buscarEnderecosPorUsuarioId(usuarioLogado.getId()));
        model.addAttribute("itens", pedidoService.getItensDoCarrinho());
        return "checkout";
    }

    @PostMapping("/finalizar-compra")
    public String finalizarCompra(@RequestParam String endereco,
                                  @RequestParam String formaPagamento,
                                  Model model) {

        Pedido pedido = pedidoService.criarPedido(endereco, formaPagamento);
        model.addAttribute("pedido", pedido);
        return "resumo-pedido"; // Retorna a página de resumo do pedido
    }
}
