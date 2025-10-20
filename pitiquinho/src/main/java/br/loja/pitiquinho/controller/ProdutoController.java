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

import java.net.HttpURLConnection;
import java.net.URL;
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


    private void configurarImagens(Produto produto) {
        String imagemOriginal = produto.getImagem();

        if (imagemOriginal == null || imagemOriginal.isEmpty()) {
            produto.setImagem("/images/imagem.jpg");
            return;
        }

        // Se for link externo
        if (imagemOriginal.startsWith("http://") || imagemOriginal.startsWith("https://")) {
            if (urlExiste(imagemOriginal)) {
                produto.setImagem(imagemOriginal);
            } else {
                produto.setImagem("/images/imagem.jpg");
            }
            return;
        }

        // Caso seja imagem local
        String[] imagensLista = imagemOriginal.split(",");
        String imagemPrincipal = imagensLista[0].trim();
        String imagemPath = "/images/" + imagemPrincipal;

        File imagemFile = new File("src/main/resources/static" + imagemPath);
        if (imagemFile.exists()) {
            produto.setImagem(imagemPath);
        } else {
            produto.setImagem("/images/imagem.jpg");
        }
    }

    private boolean urlExiste(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("HEAD");
            conexao.setConnectTimeout(3000); //
            conexao.setReadTimeout(3000);
            int responseCode = conexao.getResponseCode();
            return (responseCode >= 200 && responseCode < 400);
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/produtos")
    public String pesquisarProdutos(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "categoria", required = false) String categoria,
            Model model,
            HttpSession session) {

        // Recupera todos os produtos
        List<Produto> produtos = produtoService.findAll();

        // Filtra por nome
        if (nome != null && !nome.isEmpty()) {
            produtos.removeIf(p -> !p.getNome().toLowerCase().contains(nome.toLowerCase()));
        }

        // Filtra por categoria
        if (categoria != null && !categoria.isEmpty()) {
            produtos.removeIf(p -> !categoria.equalsIgnoreCase(p.getCategoria()));
        }

        // Configura as imagens dos produtos
        produtos.forEach(this::configurarImagens);

        model.addAttribute("produtos", produtos);
        model.addAttribute("nome", nome);
        model.addAttribute("selectedCategoria", categoria);

        // Usuário da sessão
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        return "produtos";
    }



    @GetMapping("/produtos/detalhe/{id}")
    public String detalheProduto(@PathVariable Long id, Model model, HttpSession session) {
        Produto produto = produtoService.findById(id);
        List<Produto> produtosRelacionados = produtoService.buscarProdutosRelacionados(produto);

        model.addAttribute("produto", produto);
        model.addAttribute("produtosRelacionados", produtosRelacionados);

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);

        return "produto-detalhe";
    }




}
