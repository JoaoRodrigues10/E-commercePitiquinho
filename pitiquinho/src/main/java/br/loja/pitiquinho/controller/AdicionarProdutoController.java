package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/adm")
public class AdicionarProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/adicionar-produto")
    public String mostrarFormularioAdicionarProduto(Model model) {
        return "adicionar-produto-adm";
    }

    @PostMapping("/adicionar-produto")
    public String adicionarProduto(@RequestParam String nome,
                                   @RequestParam String descricaoDetalhada,
                                   @RequestParam String preco,
                                   @RequestParam Integer quantidadeEmEstoque,
                                   @RequestParam String categoria,
                                   @RequestParam BigDecimal avaliacao,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {


        //if (produtoRepository.findByNome(nome) != null) {
         //   redirectAttributes.addFlashAttribute("error", "O nome do produto já está em uso");
         //   redirectAttributes.addFlashAttribute("nome", nome);
        //    redirectAttributes.addFlashAttribute("descricao", descricaoDetalhada);
        //    redirectAttributes.addFlashAttribute("descricaoDetalhada", descricaoDetalhada);
        //    redirectAttributes.addFlashAttribute("preco", preco);
        //    redirectAttributes.addFlashAttribute("quantidadeEmEstoque", quantidadeEmEstoque);
         //   redirectAttributes.addFlashAttribute("categoria", categoria);
         //   redirectAttributes.addFlashAttribute("avaliacao", avaliacao);
        //    return "redirect:/adm/adicionar-produto";
        //}

        BigDecimal precoDecimal = new BigDecimal(preco.replace("R$ ", "").replace(".", "").replace(",", "."));

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricaoDetalhada(descricaoDetalhada);
        produto.setPreco(precoDecimal);
        produto.setQuantidadeEmEstoque(quantidadeEmEstoque);
        produto.setCategoria(categoria);
        produto.setAvaliacao(avaliacao);
        produto.setAtivo(true);

        produtoRepository.save(produto);

        redirectAttributes.addFlashAttribute("success", "Produto adicionado com sucesso");
        return "redirect:/adm/lista-produto";
    }
}
