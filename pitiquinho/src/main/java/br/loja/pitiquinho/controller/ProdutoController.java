package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.io.File;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoService.findAll();
        produtos.forEach(produto -> configurarImagem(produto));
        model.addAttribute("produtos", produtos);
        return "produtos";
    }

    @GetMapping("/produtos/detalhe/{id}")
    public String detalheProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoService.findById(id);


        if (produto == null) {
            produto = new Produto();
            produto.setImagem("/images/imagem.jpg");
            model.addAttribute("produto", produto);
            return "produto-detalhe";

        }

        configurarImagem(produto); 
        model.addAttribute("produto", produto);
        return "produto-detalhe";
    }


    private void configurarImagem(Produto produto) {
        String imagemPath;
        if (produto.getImagem() == null || produto.getImagem().isEmpty()) {
            imagemPath = "/images/imagem.jpg";
        } else {
            imagemPath = "/images/" + produto.getImagem();
        }

        File imagemFile = new File("src/main/resources/static" + imagemPath);
        if (!imagemFile.exists()) {
            System.out.println("Arquivo não encontrado: " + imagemFile.getPath());
            imagemPath = "/images/imagem.jpg";
        }

        produto.setImagem(imagemPath);
    }

}
