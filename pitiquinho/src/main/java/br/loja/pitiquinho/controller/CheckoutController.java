package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.*;
import br.loja.pitiquinho.service.EnderecoService;
import br.loja.pitiquinho.service.PedidoService;
import br.loja.pitiquinho.service.ProdutoService;
import br.loja.pitiquinho.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/finalizar")
public class CheckoutController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/checkout-item")
    @ResponseBody
    public ResponseEntity<Void> finalizarCheckout(@RequestBody List<ItemPedido> itensCarrinho, HttpSession session) {


        session.setAttribute("itensCarrinho", itensCarrinho);

        for (ItemPedido item : itensCarrinho) {
            Produto produto = produtoService.findById(item.getId());

            if (produto != null) {
                item.setId(0L);
                item.setProduto(produto);
                item.setNome(produto.getNome());
                item.setPreco(produto.getPreco());

            }

        }

        return ResponseEntity.ok().build();
    }



    @GetMapping("/checkout")
    public String mostrarCheckout(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        session.setAttribute("usuario", usuario);

        if (usuario == null || usuario.getGrupo() == null || usuario.getGrupo().isEmpty()) {
            return "redirect:/login";
        }

        Pedido pedido = new Pedido();
        pedido.setEndereco(null);
        pedido.setFormaPagamento(null);
        pedido.setStatus("aguardando pagamento");

        List<ItemPedido> itensCarrinho = (List<ItemPedido>) session.getAttribute("itensCarrinho");

        BigDecimal total = BigDecimal.ZERO;
        if (itensCarrinho != null) {
            for (ItemPedido item : itensCarrinho) {
                BigDecimal preco = item.getPreco();
                BigDecimal quantidade = BigDecimal.valueOf(item.getQuantidade());
                BigDecimal subtotal = preco.multiply(quantidade);
                total = total.add(subtotal);
                item.setPedido(pedido);
            }
        }

        pedido.setTotal(total);

        model.addAttribute("enderecos", enderecoService.buscarEnderecosPorUsuarioIdEntrega(usuario.getId()));
        model.addAttribute("itensCarrinho", itensCarrinho);
        model.addAttribute("pedido", pedido);
        session.setAttribute("usuario", usuario);
        return "checkout";
    }


    @PostMapping("/finalizar-compra")
    public String finalizarCompra(HttpSession session,
                                  @RequestParam Long endereco,
                                  @RequestParam String formaPagamento,
                                  @RequestParam(required = false) String numeroCartao,
                                  @RequestParam(required = false) String codigoSeguranca,
                                  @RequestParam(required = false) String nomeTitular,
                                  @RequestParam(required = false) String vencimento,
                                  @RequestParam(required = false) Integer parcelas,
                                  @RequestParam(required = false) Integer opcaoFrete,
                                  Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        session.setAttribute("usuario", usuario);
        if (usuario == null || usuario.getGrupo() == null || usuario.getGrupo().isEmpty()) {
            return "redirect:/login";
        }

        List<ItemPedido> itensCarrinho = (List<ItemPedido>) session.getAttribute("itensCarrinho");
        if (itensCarrinho == null || itensCarrinho.isEmpty()) {
            model.addAttribute("erro", "Seu carrinho está vazio. Adicione itens antes de finalizar a compra.");
            return "checkout";
        }


        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEndereco(enderecoService.buscarEnderecoPorId(endereco));
        pedido.setFormaPagamento(formaPagamento);
        pedido.setStatus("aguardando pagamento");
        pedido.setValorFrete(BigDecimal.valueOf(opcaoFrete));

        BigDecimal total = BigDecimal.ZERO;

        List<ItemPedido> itensPedido = new ArrayList<>();

        for (ItemPedido item : itensCarrinho) {

            BigDecimal preco = item.getPreco();
            BigDecimal quantidade = BigDecimal.valueOf(item.getQuantidade());
            BigDecimal subtotal = preco.multiply(quantidade);
            total = total.add(subtotal);

            ItemPedido novoItem = new ItemPedido();
            novoItem.setProduto(item.getProduto());
            novoItem.setNome(item.getNome());
            novoItem.setPreco(preco);
            novoItem.setQuantidade(item.getQuantidade());
            novoItem.setPedido(pedido);
            itensPedido.add(novoItem);
        }

        pedido.setTotal(total);
        pedidoService.salvarPedido(pedido, itensPedido);


        session.removeAttribute("itensCarrinho");


        model.addAttribute("usuario", usuario);
        session.setAttribute("usuario", usuario);

        return "redirect:/meus-pedidos/" + usuario.getId();

    }



    private double calcularValorFrete(Integer opcaoFrete) {
        switch (opcaoFrete) {
            case 10:
                return 10.00;
            case 17:
                return 17.00;
            case 25:
                return 25.00;
            default:
                return 0.00;
        }
    }



}
