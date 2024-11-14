package br.loja.pitiquinho.repository;

import br.loja.pitiquinho.model.Pedido;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuarioId(Long usuarioId);

    default List<Pedido> findAllOrderByDataDesc() {
        return findAll(Sort.by(Sort.Direction.DESC, "dataCriacao"));
    }
}
