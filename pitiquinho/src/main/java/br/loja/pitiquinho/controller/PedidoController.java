package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Pedido;
import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.PedidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // ================= PEDIDOS DO USUÁRIO LOGADO =================
    @GetMapping("/meus-pedidos")
    public String listarPedidos(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/login";

        List<Pedido> pedidos = pedidoService.obterPedidosPorCliente(usuario.getId());
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("usuario", usuario);

        return "meus-pedidos";
    }

    @GetMapping("/detalhes/{pedidoId}")
    public String detalhesPedido(@PathVariable("pedidoId") Long pedidoId, Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) return "redirect:/login";

        Pedido pedido = pedidoService.obterPedidoPorId(pedidoId);
        model.addAttribute("pedido", pedido);
        model.addAttribute("itens", pedido.getItens());
        model.addAttribute("usuario", usuario);

        return "detalhes-pedido";
    }

    // ================= PEDIDOS ADMIN / GERAL =================
    @GetMapping("/listar")
    public String listarTodosPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        model.addAttribute("pedidos", pedidos);
        return "listar-pedidos";
    }

    @GetMapping("/editar/{id}")
    public String editarPedido(@PathVariable("id") Long id, Model model) {
        Pedido pedido = pedidoService.obterPedidoPorId(id);
        model.addAttribute("pedido", pedido);
        return "editar-pedido";
    }

    @PostMapping("/salvar")
    public String salvarPedido(@ModelAttribute("pedido") Pedido pedido) {
        Pedido pedidoExistente = pedidoService.obterPedidoPorId(pedido.getId());
        if (pedidoExistente != null) {
            pedidoExistente.setStatus(pedido.getStatus());
            pedidoService.salvarPedido(pedidoExistente);
        }
        return "redirect:/pedidos/listar";
    }
}
