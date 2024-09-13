package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListarProdutosController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/adm/lista-produto")
    public String listaProduto(Model model) {
        List<Produto> produtos = produtoService.listarTodosProdutos();
        model.addAttribute("produtos", produtos);

        for (Produto produto : produtos) {
            System.out.println("ID: " + produto.getId() +
                    ", Nome: " + produto.getNome() +
                    ", Descrição: " + produto.getDescricaoDetalhada() +
                    ", Preço: " + produto.getPreco() +
                    ", Quantidade em Estoque: " + produto.getQuantidadeEmEstoque() +
                    ", Categoria: " + produto.getCategoria() +
                    ", Ativo: " + produto.getAtivo());
        }

        return "lista-produto-adm";
    }
}
