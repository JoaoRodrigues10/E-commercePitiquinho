package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
public class AdicionarProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Value("${upload.path}")
    private String uploadPath;

    private static final Logger logger = Logger.getLogger(AdicionarProdutoController.class.getName());

    @GetMapping("/adicionar-produto")
    public String mostrarFormularioAdicionarProduto(Model model) {
        return "adicionar-produto";
    }

    @PostMapping("/adicionar-produto")
    public String adicionarProduto(@RequestParam String nome,
                                   @RequestParam String descricao,
                                   @RequestParam String preco,
                                   @RequestParam Integer quantidadeEmEstoque,
                                   @RequestParam String categoria,
                                   @RequestParam BigDecimal avaliacao,
                                   @RequestParam("imagem") MultipartFile imagem,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        String nomeArquivoOriginal = imagem.getOriginalFilename();
        String extensao = "";
        if (nomeArquivoOriginal != null && nomeArquivoOriginal.contains(".")) {
            extensao = nomeArquivoOriginal.substring(nomeArquivoOriginal.lastIndexOf("."));
        }
        String nomeArquivo = UUID.randomUUID().toString().replaceAll("-", "") + extensao.toLowerCase();

        Path caminhoSalvar = Paths.get(uploadPath, nomeArquivo).toAbsolutePath();
        Path diretorio = caminhoSalvar.getParent();

        logger.info("Caminho absoluto para salvar a imagem: " + caminhoSalvar.toString());

        if (!Files.exists(diretorio)) {
            try {
                Files.createDirectories(diretorio);
                logger.info("Diretório criado: " + diretorio.toString());
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("error", "Erro ao criar diretório para imagens.");
                return "redirect:/adicionar-produto";
            }
        }

        try {
            imagem.transferTo(caminhoSalvar.toFile());
            logger.info("Imagem salva com sucesso: " + caminhoSalvar.toString());
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro ao fazer upload da imagem.");
            return "redirect:/adicionar-produto";
        }

        BigDecimal precoDecimal = new BigDecimal(preco.replace("R$ ", "").replace(".", "").replace(",", "."));

        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(precoDecimal);
        produto.setQuantidadeEmEstoque(quantidadeEmEstoque);
        produto.setCategoria(categoria);
        produto.setAvaliacao(avaliacao);
        produto.setAtivo(true);
        produto.setImagem(nomeArquivo);

        produtoRepository.save(produto);

        redirectAttributes.addFlashAttribute("success", "Produto adicionado com sucesso");
        return "redirect:/lista-produto";
    }
}
