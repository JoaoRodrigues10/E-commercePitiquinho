package br.loja.pitiquinho.repository;

import br.loja.pitiquinho.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Produto findByNome(String nome);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);


    List<Produto> findByNomeContaining(String nome);
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByNomeContainingIgnoreCaseAndCategoriaIgnoreCase(String nome, String categoria);

    @Query("SELECT DISTINCT p.categoria FROM Produto p")
    List<String> findDistinctCategorias();

    List<Produto> findTop4ByCategoriaOrNomeContainingIgnoreCase( String categoria, String nome);



}
