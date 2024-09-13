package br.loja.pitiquinho.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Long id;

    @Column(name = "ds_nome", nullable = false, length = 200)
    @Size(max = 200, message = "O nome do produto não pode exceder 200 caracteres")
    private String nome;

    @DecimalMin(value = "1.0", message = "A avaliação mínima deve ser 1")
    @DecimalMax(value = "5.0", message = "A avaliação máxima deve ser 5")
    @Digits(integer = 1, fraction = 1, message = "A avaliação deve ser em incrementos de 0,5")
    @Column(name = "nr_avaliacao", nullable = false)
    private BigDecimal avaliacao;

    @Column(name = "ds_descricao_detalhada", length = 2000)
    @Size(max = 2000, message = "A descrição detalhada não pode exceder 2000 caracteres")
    private String descricaoDetalhada;

    @Column(name = "nr_preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "nr_quantidadeestoque", nullable = false)
    private Integer quantidadeEmEstoque;

    @Column(name = "ds_categoria", length = 50)
    private String categoria;

    @Column(name = "bo_ativo", nullable = false)
    private Boolean ativo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(BigDecimal avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
