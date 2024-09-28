package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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

            return "redirect:/produtos";
        }

        configurarImagem(produto);
        model.addAttribute("produto", produto);
        return "produto-detalhe";
    }

    private void configurarImagem(Produto produto) {
        if (produto.getImagem() == null || produto.getImagem().isEmpty()) {
            produto.setImagem("/images/imagem.jpg");
        } else {
            produto.setImagem("/images/" + produto.getImagem());
        }
    }
}
