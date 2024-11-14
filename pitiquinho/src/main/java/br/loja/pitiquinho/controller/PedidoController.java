package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Pedido;
import br.loja.pitiquinho.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/pedidos")
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        model.addAttribute("pedidos", pedidos);
        return "listar-pedidos";
    }

    @GetMapping("/pedidos/editar/{id}")
    public String editarPedido(@PathVariable("id") Long id, Model model) {
        Pedido pedido = pedidoService.obterPedidoPorId(id);
        model.addAttribute("pedido", pedido);
        return "editar-pedido";
    }

    @PostMapping("/pedidos/salvar")
    public String salvarPedido(@ModelAttribute("pedido") Pedido pedido) {
        Pedido pedidoExistente = pedidoService.obterPedidoPorId(pedido.getId());
        if (pedidoExistente != null) {
            pedidoExistente.setStatus(pedido.getStatus());
            pedidoService.salvarPedido(pedidoExistente);
        }
        return "redirect:/pedidos";
    }



}
