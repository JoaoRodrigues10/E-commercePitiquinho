package br.loja.pitiquinho.service;

import br.loja.pitiquinho.model.ItemPedido;
import br.loja.pitiquinho.model.Pedido;
import br.loja.pitiquinho.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoService itemPedidoService;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAllOrderByDataDesc();
    }

    public List<Pedido> obterPedidosPorCliente(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    public Pedido salvarPedido(Pedido pedido, List<ItemPedido> itensPedido) {
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        for (ItemPedido item : itensPedido) {
            item.setPedido(pedidoSalvo);
            itemPedidoService.salvarItemPedido(item);
        }


        return pedidoSalvo;
    }

    public void salvarPedido(Pedido pedido) {
        pedidoRepository.save(pedido);
    }

    public Pedido obterPedidoPorId(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElse(null);
    }
}
