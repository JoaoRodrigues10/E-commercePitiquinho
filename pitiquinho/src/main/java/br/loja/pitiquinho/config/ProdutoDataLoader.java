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
        criarProduto("Roupa", "Roupa de bebê confeccionada em algodão macio, ideal para proporcionar conforto ao longo do dia. Com design fofo e costuras reforçadas, é perfeita para uso diário e fácil de lavar.",
                BigDecimal.valueOf(29.99), 100, "Vestuário", true, BigDecimal.valueOf(4.5), "roupa.jpg");

        criarProduto("Carrinho", "Carrinho de bebê dobrável, leve e seguro, com cinto de cinco pontos e assento reclinável. Projetado para passeios confortáveis, com rodas robustas e freios traseiros.",
                BigDecimal.valueOf(549.99), 200, "Acessórios de Passeio", true, BigDecimal.valueOf(4.0), "carrinho.jpg");

        criarProduto("Chupeta", "Chupeta ortodôntica de silicone, macia e segura para o uso diário. Desenvolvida para auxiliar no desenvolvimento oral do bebê, reduzindo o risco de má oclusão.",
                BigDecimal.valueOf(99.99), 50, "Acessórios de Alimentação", false, BigDecimal.valueOf(3.5), "chupeta.jpg");
    }


    private void criarProduto(final String nome, final String descricao, final BigDecimal preco,
                              final Integer quantidadeEmEstoque, final String categoria, final Boolean ativo,
                              final BigDecimal avaliacao, final String imagem) {
        Produto produto = new Produto();


        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeEmEstoque(quantidadeEmEstoque);
        produto.setCategoria(categoria);
        produto.setAtivo(ativo);
        produto.setAvaliacao(avaliacao);
        produto.setImagem(imagem);


        produtoRepository.save(produto);
    }
}
