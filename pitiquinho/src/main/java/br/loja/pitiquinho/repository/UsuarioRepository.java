package br.loja.pitiquinho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import br.loja.pitiquinho.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
