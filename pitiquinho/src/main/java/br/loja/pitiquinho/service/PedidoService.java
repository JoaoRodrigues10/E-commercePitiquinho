package br.loja.pitiquinho.service;

import br.loja.pitiquinho.model.ItemPedido;
import br.loja.pitiquinho.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    public List<ItemPedido> getItensDoCarrinho() {
        return List.of();
    }

    public Pedido criarPedido(String endereco, String formaPagamento) {

        Pedido pedido = new Pedido();
        pedido.setStatus("aguardando pagamento");

        return pedido;
    }
}

