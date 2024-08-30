package br.loja.pitiquinho.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.loja.pitiquinho.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}
