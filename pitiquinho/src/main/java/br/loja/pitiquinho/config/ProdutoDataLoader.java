package br.loja.pitiquinho.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.repository.ProdutoRepository;

import java.math.BigDecimal;

@Component
public class ProdutoDataLoader implements CommandLineRunner {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (produtoRepository.count() == 0) { // Verifica se não há produtos no banco de dados
            criarProduto("Produto A", "Descrição do Produto A", BigDecimal.valueOf(29.99), 100, "Categoria 1", true);
            criarProduto("Produto B", "Descrição do Produto B", BigDecimal.valueOf(49.99), 200, "Categoria 2", true);
            criarProduto("Produto C", "Descrição do Produto C", BigDecimal.valueOf(99.99), 50, "Categoria 1", false);
        }
    }

    private Produto criarProduto(String nome, String descricao, BigDecimal preco, Integer quantidadeEmEstoque, String categoria, Boolean ativo) {
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeEmEstoque(quantidadeEmEstoque);
        produto.setCategoria(categoria);
        produto.setAtivo(ativo);
        return produtoRepository.save(produto);
    }
}
