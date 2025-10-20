package br.loja.pitiquinho.service;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }


    public Page<Produto> listarTodosPaginado(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Page<Produto> buscarPorNomePaginado(String nome, Pageable pageable) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    }


    public Optional<Produto> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto criarProduto(Produto produto) {
        if (produto.getAtivo() == null) {
            produto.setAtivo(true);
        }
        return produtoRepository.save(produto);
    }


    public Produto atualizarProduto(Long id, Produto produto) {
        produto.setId(id);
        return produtoRepository.save(produto);
    }


    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public void atualizarStatusProduto(Long id, boolean status) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        produto.setAtivo(status);
        produtoRepository.save(produto);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }


    public List<Produto> filtrarProdutos(String nome, String categoria) {
        if (nome != null && !nome.isEmpty() && categoria != null && !categoria.isEmpty()) {
            return produtoRepository.findByNomeContainingIgnoreCaseAndCategoriaIgnoreCase(nome, categoria);
        } else if (nome != null && !nome.isEmpty()) {
            return produtoRepository.findByNomeContainingIgnoreCase(nome);
        } else if (categoria != null && !categoria.isEmpty()) {
            return produtoRepository.findByCategoria(categoria);
        } else {
            return produtoRepository.findAll();
        }
    }

    public List<Produto> buscarProdutosRelacionados(Produto produto) {
        // Busca até 4 produtos da mesma categoria ou com nome parecido,
        // excluindo o próprio produto atual pelo ID
        List<Produto> relacionados = produtoRepository
                .findTop4ByCategoriaOrNomeContainingIgnoreCase(produto.getCategoria(), produto.getNome());

        // Filtra o próprio produto para garantir que não apareça
        relacionados.removeIf(p -> p.getId().equals(produto.getId()));

        return relacionados;
    }



}
