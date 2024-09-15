package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/adm")
public class AlterarProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @RequestMapping("/alterar-produto")
    public String alterarProduto(@RequestParam Long id,
                                 @RequestParam(required = false) String nome,
                                 @RequestParam(required = false) String descricao,
                                 @RequestParam(required = false) Double preco,
                                 @RequestParam(required = false) Integer quantidade,
                                 @RequestParam(required = false) String categoria,
                                 Model model) {

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (nome != null && !nome.isEmpty()) {
            produto.setNome(nome);
        }
        if (descricao != null && !descricao.isEmpty()) {
            produto.setDescricao(descricao);
        }
        if (preco != null) {
            produto.setPreco(BigDecimal.valueOf(preco));
        }
        if (quantidade != null) {
            produto.setQuantidadeEmEstoque(quantidade);
        }
        if (categoria != null && !categoria.isEmpty()) {
            produto.setCategoria(categoria);
        }

        produtoRepository.save(produto);

        return "redirect:/adm/lista-produto";
    }


    @PostMapping("/alterar-produto/desativar")
    public String desativarProduto(@RequestParam Long id, @RequestParam boolean currentStatus, RedirectAttributes redirectAttributes) {

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setAtivo(!currentStatus);

        produtoRepository.save(produto);

        String mensagem = currentStatus ? "Produto desativado com sucesso" : "Produto ativado com sucesso";
        redirectAttributes.addFlashAttribute("success", mensagem);

        return "redirect:/adm/lista-produto";
    }
}
