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
                BigDecimal.valueOf(29.99), 100, "Vestuário", true, BigDecimal.valueOf(4.5), "https://images.tcdn.com.br/img/img_prod/670007/90_body_bebe_algodao_manga_curta_gola_americana_branco_comfort_13973_1_507729b51520db4858a4d6e93a55282d.png");

        criarProduto("Carrinho", "Carrinho de bebê dobrável, leve e seguro, com cinto de cinco pontos e assento reclinável. Projetado para passeios confortáveis, com rodas robustas e freios traseiros.",
                BigDecimal.valueOf(549.99), 200, "Acessórios de Passeio", true, BigDecimal.valueOf(4.0), "https://m.media-amazon.com/images/I/715XE6j-M0L._AC_UF894,1000_QL80_.jpg");

        criarProduto("Chupeta", "Chupeta ortodôntica de silicone, macia e segura para o uso diário. Desenvolvida para auxiliar no desenvolvimento oral do bebê, reduzindo o risco de má oclusão.",
                BigDecimal.valueOf(99.99), 50, "Acessórios de Alimentação", false, BigDecimal.valueOf(3.5), "https://drogariaemcasa.com.br/media/catalog/product/cache/1/thumbnail/600x/17f82f742ffe127f42dca9de82fb58b1/o/k/ok_2_4.jpg");

        criarProduto("Mamadeira", "Mamadeira de vidro com bico de silicone, ideal para alimentação de bebês a partir de 3 meses. Antivazamento e fácil de limpar.",
                BigDecimal.valueOf(39.99), 150, "Acessórios de Alimentação", true, BigDecimal.valueOf(4.2), "https://down-br.img.susercontent.com/file/b577dc86397d61c970e605e6bafe3c7c");

        criarProduto("Fralda", "Fralda descartável de alta absorção, ideal para manter o bebê seco por mais tempo. Disponível em diversos tamanhos.",
                BigDecimal.valueOf(89.99), 300, "Higiene e Cuidado", true, BigDecimal.valueOf(4.8), "https://http2.mlstatic.com/D_NQ_NP_783359-MLB75682233983_042024-O.webp");

        criarProduto("Berço", "Berço portátil de madeira com colchão antialérgico. Segurança e conforto para o bebê com fácil montagem.",
                BigDecimal.valueOf(799.99), 50, "Móveis e Decoração", true, BigDecimal.valueOf(4.7), "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQ6efw3kRFb51cFcvQbAKzRfGAqCZxkLjUGA&s");


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
