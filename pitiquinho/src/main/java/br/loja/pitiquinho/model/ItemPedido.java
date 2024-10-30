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
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    
    @Column(name = "fk_pedido_id", nullable = false)
    private Long pedidoId;

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

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
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
