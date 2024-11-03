package br.loja.pitiquinho.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "fk_produto_id", nullable = false)
    private Produto produto; // Associação com a entidade Produto

    @Column(name = "ds_nome", nullable = false, length = 200)
    @Size(max = 200, message = "O nome do item não pode exceder 200 caracteres")
    private String nome;

    @Column(name = "nr_preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco; // Preço do item

    @Column(name = "nr_quantidade", nullable = false)
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private Integer quantidade; // Quantidade do item no pedido

    public ItemPedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
