package br.loja.pitiquinho.repository;

import br.loja.pitiquinho.model.EnderecoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnderecoEntregaRepository extends JpaRepository<EnderecoEntrega, Long> {
    List<EnderecoEntrega> findByUsuarioId(Long usuarioId);
}
