package br.loja.pitiquinho.service;

import br.loja.pitiquinho.model.ItemPedido;
import br.loja.pitiquinho.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public void salvarItemPedido(ItemPedido item) {
        itemPedidoRepository.save(item);
    }
}

