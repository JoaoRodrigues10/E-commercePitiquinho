package br.loja.pitiquinho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.loja.pitiquinho.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    Cliente findByEmail(String email);
    Cliente findByCpf(String cpf); // Adicionado para encontrar por CPF
}
