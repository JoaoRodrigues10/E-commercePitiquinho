package br.loja.pitiquinho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import br.loja.pitiquinho.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Usuario findByCpf(String cpf);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha = :senha")
    public Usuario findByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);

    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Usuario findByEmail(@Param("email") String email);
}
