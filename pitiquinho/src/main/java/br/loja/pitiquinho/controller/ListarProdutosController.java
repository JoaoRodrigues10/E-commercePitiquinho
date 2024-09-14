package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ListarProdutosController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/adm/lista-produto")
    public String listarProdutos(@RequestParam(value = "nome", required = false) String nome, Model model) {
        List<Produto> produtos;

        if (nome != null && !nome.isEmpty()) {
            produtos = produtoService.buscarPorNome(nome);
        } else {
            produtos = produtoService.listarTodos();
        }

        model.addAttribute("produtos", produtos);
        return "lista-produto-adm";
    }
}
