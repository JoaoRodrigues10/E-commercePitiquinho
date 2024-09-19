package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.mysql.cj.conf.PropertyKey.logger;
@Controller
@RequestMapping("/adm")
public class AlterarProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/alterar-produto")
    public String alterarProduto(@RequestParam Long id,
                                 @RequestParam(required = false) String nome,
                                 @RequestParam(required = false) String descricao,
                                 @RequestParam(required = false) String preco,
                                 @RequestParam(required = false) Integer quantidade,
                                 @RequestParam(required = false) String categoria,
                                 @RequestParam(required = false) MultipartFile imagem,
                                 Model model) {

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (imagem != null && !imagem.isEmpty()) {
            String nomeArquivoOriginal = imagem.getOriginalFilename();
            String extensao = "";
            if (nomeArquivoOriginal != null && nomeArquivoOriginal.contains(".")) {
                extensao = nomeArquivoOriginal.substring(nomeArquivoOriginal.lastIndexOf("."));
            }
            String nomeArquivo = UUID.randomUUID().toString().replaceAll("-", "") + extensao.toLowerCase();

            Path caminhoSalvar = Paths.get(uploadPath, nomeArquivo).toAbsolutePath();
            Path diretorio = caminhoSalvar.getParent();

            if (!Files.exists(diretorio)) {
                try {
                    Files.createDirectories(diretorio);
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("error", "Erro ao criar diretório para imagens.");
                    return "redirect:/adm/lista-produto";
                }
            }

            try {
                imagem.transferTo(caminhoSalvar.toFile());
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Erro ao fazer upload da imagem.");
                return "redirect:/adm/lista-produto";
            }

            produto.setImagem(nomeArquivo);
        }

        if (nome != null && !nome.isEmpty()) {
            produto.setNome(nome);
        }
        if (descricao != null && !descricao.isEmpty()) {
            produto.setDescricao(descricao);
        }
        if (preco != null && !preco.isEmpty()) {
            produto.setPreco(new BigDecimal(preco.replace("R$ ", "").replace(".", "").replace(",", ".")));
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
