package br.loja.pitiquinho.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Long id;

    @Column(name = "ds_nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "ds_descricao")
    private String descricao;

    @Column(name = "nr_preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "nr_quantidadeestoque", nullable = false)
    private Integer quantidadeEmEstoque;

    @Column(name = "ds_categoria", length = 50)
    private String categoria;

    @Column(name = "bo_ativo", nullable = false)

    private Boolean boAtivo;


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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        return boAtivo;
    }

    public void setAtivo(Boolean ativo) {
        this.boAtivo = boAtivo;
    }
}
