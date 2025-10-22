package br.loja.pitiquinho.repository;

import br.loja.pitiquinho.model.Endereco;
import br.loja.pitiquinho.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByUsuarioId(Long usuarioId);
    List<Endereco> findByUsuarioIdAndTipoEndereco(Long usuarioId, String tipoEndereco);

    Endereco findByIdAndUsuarioId(Long id, Long usuarioId);
    List<Endereco> findByUsuario(Usuario usuario);
    Endereco findByUsuarioAndPadraoTrue(Usuario usuario);
    long countByUsuarioId(Long usuarioId);
    Endereco findFirstByUsuarioIdAndPadraoTrue(Long usuarioId);

}
