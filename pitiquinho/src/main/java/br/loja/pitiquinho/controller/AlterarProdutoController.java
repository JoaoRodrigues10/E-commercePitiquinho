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
                                 @RequestParam(required = false) MultipartFile imagemPrincipal, // Imagem Principal
                                 @RequestParam(required = false) MultipartFile[] imagensAdicionais, // Imagens adicionais
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Atualiza a imagem principal, se fornecida
        if (imagemPrincipal != null && !imagemPrincipal.isEmpty()) {
            String nomeImagemPrincipal = processarImagem(imagemPrincipal);
            if (nomeImagemPrincipal != null) {
                produto.setImagem(nomeImagemPrincipal); // Define a nova imagem principal
            } else {
                redirectAttributes.addFlashAttribute("error", "Erro ao fazer upload da imagem principal.");
                return "redirect:/alterar-produto?id=" + id; // Redireciona para o formulário de alteração
            }
        }

        // Atualiza imagens adicionais, se fornecidas
        StringBuilder nomesImagensAdicionais = new StringBuilder();
        if (imagensAdicionais != null) {
            for (int i = 0; i < imagensAdicionais.length && i < 4; i++) {
                MultipartFile imagemAdicional = imagensAdicionais[i];
                if (imagemAdicional != null && !imagemAdicional.isEmpty()) {
                    String nomeArquivoAdicional = processarImagem(imagemAdicional);
                    if (nomeArquivoAdicional != null) {
                        if (nomesImagensAdicionais.length() > 0) {
                            nomesImagensAdicionais.append(",");
                        }
                        nomesImagensAdicionais.append(nomeArquivoAdicional);
                    } else {
                        redirectAttributes.addFlashAttribute("error", "Erro ao fazer upload de uma das imagens adicionais.");
                        return "redirect:/alterar-produto?id=" + id; // Redireciona para o formulário de alteração
                    }
                }
            }
            // Adiciona as imagens adicionais ao produto
            if (nomesImagensAdicionais.length() > 0) {
                if (produto.getImagem() != null && !produto.getImagem().isEmpty()) {
                    produto.setImagem(produto.getImagem() + "," + nomesImagensAdicionais.toString());
                } else {
                    produto.setImagem(nomesImagensAdicionais.toString());
                }
            }
        }

        // Atualiza outros atributos do produto
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
        redirectAttributes.addFlashAttribute("success", "Produto alterado com sucesso");
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


    @PostMapping("/alterar-produto/desativar")
    public String desativarProduto(@RequestParam Long id, @RequestParam boolean currentStatus, RedirectAttributes redirectAttributes) {

        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setAtivo(!currentStatus);

        produtoRepository.save(produto);

        String mensagem = currentStatus ? "Produto desativado com sucesso" : "Produto ativado com sucesso";
        redirectAttributes.addFlashAttribute("success", mensagem);

        return "redirect:/lista-produto";
    }
}
