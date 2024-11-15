package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/")
    public String listarProdutos(Model model, HttpSession session) {
        List<Produto> produtos = produtoService.findAll();
        produtos.forEach(produto -> configurarImagens(produto));
        model.addAttribute("produtos", produtos);

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        return "produtos";
    }

    @GetMapping("/produtos/detalhe/{id}")
    public String detalheProduto(@PathVariable Long id, Model model, HttpSession session) {
        Produto produto = produtoService.findById(id);
        model.addAttribute("produto", produto);

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

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

    @GetMapping("/produtos")
    public String listarProdutos(@RequestParam(required = false) String nome,
                                 @RequestParam(required = false) String categoria,
                                 Model model, HttpSession session) {
        List<Produto> produtos = produtoService.filtrarProdutos(nome, categoria);
        model.addAttribute("produtos", produtos);

        List<String> categorias = produtoService.listarCategorias();
        model.addAttribute("categorias", categorias);

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        return "produtos";
    }

}
