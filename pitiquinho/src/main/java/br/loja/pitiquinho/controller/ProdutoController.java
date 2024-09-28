package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoService.findAll();
        produtos.forEach(produto -> configurarImagens(produto));
        model.addAttribute("produtos", produtos);
        return "produtos";
    }

    @GetMapping("/produtos/detalhe/{id}")
    public String detalheProduto(@PathVariable Long id, Model model) {
        Produto produto = produtoService.findById(id);
        model.addAttribute("produto", produto);
        return "produto-detalhe";
    }

    private void configurarImagens(Produto produto) {
        if (produto.getImagem() == null || produto.getImagem().isEmpty()) {
            produto.setImagem("/images/imagem.jpg");
        } else {

            String[] imagensLista = produto.getImagem().split(",");
            String imagemPrincipal = imagensLista[0].trim();
            String imagemPath = "/images/" + imagemPrincipal;

            File imagemFile = new File("src/main/resources/static" + imagemPath);
            if (imagemFile.exists()) {
                produto.setImagem(imagemPath);
            } else {
                System.out.println("Arquivo não encontrado: " + imagemFile.getPath());
                produto.setImagem("/images/imagem.jpg");
            }
        }
    }


}
