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
                                   @RequestParam("imagemPrincipal") MultipartFile imagemPrincipal, // Imagem Principal
                                   @RequestParam("imagensAdicionais") MultipartFile[] imagensAdicionais, // Imagens adicionais
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        StringBuilder nomesImagens = new StringBuilder();

        if (!imagemPrincipal.isEmpty()) {
            String nomeArquivoPrincipal = processarImagem(imagemPrincipal); // Método que processa a imagem (explicado a seguir)
            if (nomeArquivoPrincipal == null) {
                redirectAttributes.addFlashAttribute("error", "Erro ao fazer upload da imagem principal.");
                return "redirect:/adicionar-produto";
            }
            nomesImagens.append(nomeArquivoPrincipal);
        }

        for (int i = 0; i < imagensAdicionais.length && i < 4; i++) {
            MultipartFile imagemAdicional = imagensAdicionais[i];

            if (!imagemAdicional.isEmpty()) {
                String nomeArquivoAdicional = processarImagem(imagemAdicional);
                if (nomeArquivoAdicional == null) {
                    redirectAttributes.addFlashAttribute("error", "Erro ao fazer upload de uma das imagens adicionais.");
                    return "redirect:/adicionar-produto";
                }

                // Concatenar o nome da imagem adicional
                nomesImagens.append(",").append(nomeArquivoAdicional);
            }
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
        produto.setImagem(nomesImagens.toString()); // Define todas as imagens (a primeira é a principal)


        produtoRepository.save(produto);

        redirectAttributes.addFlashAttribute("success", "Produto adicionado com sucesso");
        return "redirect:/lista-produto";
    }

    // Método que concatena o nome da imagem com um UUID e salva a imagem no diretório de upload
    private String processarImagem(MultipartFile imagem) {
        String nomeArquivoOriginal = imagem.getOriginalFilename();
        String extensao = "";
        if (nomeArquivoOriginal != null && nomeArquivoOriginal.contains(".")) {
            extensao = nomeArquivoOriginal.substring(nomeArquivoOriginal.lastIndexOf("."));
        }
        String nomeImagem = UUID.randomUUID().toString().replaceAll("-", "") + extensao.toLowerCase();

        Path caminhoSalvar = Paths.get(uploadPath, nomeImagem).toAbsolutePath();
        Path diretorio = caminhoSalvar.getParent();

        if (!Files.exists(diretorio)) {
            try {
                Files.createDirectories(diretorio);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        try {
            imagem.transferTo(caminhoSalvar.toFile());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return nomeImagem;
    }



}
