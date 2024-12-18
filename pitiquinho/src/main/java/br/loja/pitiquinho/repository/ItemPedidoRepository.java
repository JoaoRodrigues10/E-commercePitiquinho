package br.loja.pitiquinho.repository;

import br.loja.pitiquinho.model.ItemPedido;
import br.loja.pitiquinho.model.Pedido; // Supondo que exista uma classe Pedido
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
}
