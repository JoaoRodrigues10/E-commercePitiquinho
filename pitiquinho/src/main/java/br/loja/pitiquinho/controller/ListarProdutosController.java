package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.model.Usuario;
import br.loja.pitiquinho.service.ProdutoService;
import br.loja.pitiquinho.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class ListarProdutosController {

    private final ProdutoService produtoService;

    public ListarProdutosController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/lista-produto")
    public String listarProdutos(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model,HttpSession session) {

        Usuario usuarioLogado = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuarioLogado);
        if (usuarioLogado == null || usuarioLogado.getGrupo() == null || usuarioLogado.getGrupo().isEmpty()) {
            return "redirect:/";
        }


        Pageable pageable = PageRequest.of(page, size);
        Page<Produto> produtosPage;

        if (nome != null && !nome.isEmpty()) {
            produtosPage = produtoService.buscarPorNomePaginado(nome, pageable);
        } else {
            produtosPage = produtoService.listarTodosPaginado(pageable);
        }

        model.addAttribute("produtos", produtosPage.getContent());
        model.addAttribute("currentPage", produtosPage.getNumber());
        model.addAttribute("totalPages", produtosPage.getTotalPages());

        return "lista-produto";
    }

    @GetMapping("/produto")
    public String visualizarProduto(
            @RequestParam("id") Long id,
            Model model) {

        Produto produto = produtoService.buscarProdutoPorId(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        model.addAttribute("produto", produto);

        return "redirect:/lista-produto";
    }

}
