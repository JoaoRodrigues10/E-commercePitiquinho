package br.loja.pitiquinho.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_fk", nullable = false)
    private Usuario usuario; // Relacionamento com a classe Usuario

    @ManyToOne
    @JoinColumn(name = "endereco_fk", nullable = false)
    private Endereco endereco; // Relacionamento com a classe Endereco

    @Column(name = "ds_status", nullable = false)
    private String status; // Status do pedido

    @Column(name = "vl_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total; // Valor total do pedido

    @Column(name = "vl_frete", precision = 10, scale = 2) // Novo campo para o valor do frete
    private BigDecimal valorFrete; // Valor do frete do pedido

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_criacao", nullable = false)
    private Date dataCriacao; // Data de criação do pedido

    @Column(name = "ds_forma_pagamento", nullable = false)
    private String formaPagamento;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;

    public Pedido() {
        this.dataCriacao = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getValorFrete() {
        return valorFrete; // Novo getter para o valor do frete
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete; // Novo setter para o valor do frete
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
}
