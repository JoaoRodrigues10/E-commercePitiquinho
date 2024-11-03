package br.loja.pitiquinho.service;

import br.loja.pitiquinho.model.Endereco;
import br.loja.pitiquinho.model.ItemPedido;
import br.loja.pitiquinho.model.Pedido;
import br.loja.pitiquinho.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;


    public List<ItemPedido> getItensDoCarrinho() {
        List<ItemPedido> itens = new ArrayList<>();



        return itens;
    }


    public Pedido criarPedido(Endereco endereco, String formaPagamento) {
        Pedido pedido = new Pedido();
        pedido.setEndereco(endereco);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setStatus("aguardando pagamento");

        List<ItemPedido> itens = getItensDoCarrinho();
        pedido.setItens(itens);

        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            item.setPedido(pedido);

            BigDecimal preco = item.getPreco();
            BigDecimal quantidade = BigDecimal.valueOf(item.getQuantidade());
            BigDecimal subtotal = preco.multiply(quantidade);
        }

        pedido.setTotal(total);


        return salvarPedido(pedido);
    }


    public Pedido salvarPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}
