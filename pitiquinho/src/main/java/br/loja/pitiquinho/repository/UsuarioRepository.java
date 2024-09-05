package br.loja.pitiquinho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import br.loja.pitiquinho.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario findByEmail(@Param("email") String email);

    
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmailOptional(String email);

   @Query("SELECT u FROM Usuario u WHERE u.ds_email = :usuario AND u.ds_senha = :senha")
    public Usuario buscarLogin(@Param("usuario") String usuario, @Param("senha") String senha);

}
