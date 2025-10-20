package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Pedido; // Certifique-se de que a classe Pedido está importada corretamente
import br.loja.pitiquinho.model.Usuario; // Classe de Usuário
import br.loja.pitiquinho.service.PedidoService; // Serviço para gerenciar pedidos
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/meus-pedidos")
public class ClientePedidosController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{id}")
    public String listarPedidos(@PathVariable("id") Long usuarioId, Model model,HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<Pedido> pedidos = pedidoService.obterPedidosPorCliente(usuarioId);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("usuario", usuario);
        session.setAttribute("usuario", usuario);

        return "meus-pedidos";
    }

    @GetMapping("/detalhes/{pedidoId}")
    public String detalhesPedido(@PathVariable("pedidoId") Long pedidoId, Model model,HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        session.setAttribute("usuario", usuario);
        Pedido pedido = pedidoService.obterPedidoPorId(pedidoId);

        model.addAttribute("pedido", pedido);
        model.addAttribute("itens", pedido.getItens());
        return "detalhes-pedido";
    }



}
