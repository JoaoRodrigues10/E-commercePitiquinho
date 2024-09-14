package br.loja.pitiquinho.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.repository.ProdutoRepository;

import java.math.BigDecimal;

@Component
public class ProdutoDataLoader implements CommandLineRunner {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoDataLoader(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (produtoRepository.count() == 0) {
            carregarProdutosIniciais();
        }
    }

    private void carregarProdutosIniciais() {
       criarProduto("Produto A", "Descrição do Produto A", BigDecimal.valueOf(29.99), 100, "Categoria 1", true, BigDecimal.valueOf(4.5));
       criarProduto("Produto B", "Descrição do Produto B", BigDecimal.valueOf(49.99), 200, "Categoria 2", true, BigDecimal.valueOf(4.0));
       criarProduto("Produto C", "Descrição do Produto C", BigDecimal.valueOf(99.99), 50, "Categoria 1", false, BigDecimal.valueOf(3.5));
    }

    private void criarProduto(final String nome, final String descricao, final BigDecimal preco,
                              final Integer quantidadeEmEstoque, final String categoria, final Boolean ativo,
                              final BigDecimal avaliacao) {
        Produto produto = new Produto();


        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeEmEstoque(quantidadeEmEstoque);
        produto.setCategoria(categoria);
        produto.setAtivo(ativo);
        produto.setAvaliacao(avaliacao);


        produtoRepository.save(produto);
    }
}
