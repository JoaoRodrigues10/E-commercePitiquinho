package br.loja.pitiquinho.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "tb_enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id") // Nome da coluna para o ID
    private Long id;

    @NotNull
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 00000-000")
    @Column(name = "ds_cep", nullable = false) // Nome da coluna para o CEP
    private String cep;

    @NotNull
    @Column(name = "ds_logradouro", nullable = false) // Nome da coluna para o logradouro
    private String logradouro;

    @NotNull
    @Column(name = "ds_numero", nullable = false) // Nome da coluna para o número
    private String numero;

    @Column(name = "ds_complemento") // Nome da coluna para o complemento
    private String complemento;

    @NotNull
    @Column(name = "ds_bairro", nullable = false) // Nome da coluna para o bairro
    private String bairro;

    @NotNull
    @Column(name = "ds_cidade", nullable = false) // Nome da coluna para a cidade
    private String cidade;

    @NotNull
    @Column(name = "ds_uf", nullable = false) // Nome da coluna para a UF
    private String uf;

    @NotNull
    @Column(name = "tp_endereco", nullable = false) // Nome da coluna para o tipo de endereço
    private String tipoEndereco;

    @NotNull
    @Column(name = "tg_padrao", nullable = false) // nome da coluna no banco
    private Boolean padrao = false; // valor padrão opcional


    @NotNull
    @Column(name = "usuario_fk", nullable = false) // Nome da coluna para o ID do usuário
    private Long usuarioId;

    @ManyToOne
    @JoinColumn(name = "usuario_fk", insertable = false, updatable = false)
    private Usuario usuario;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(String tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isPadrao() {
        return padrao;
    }

    public void setPadrao(boolean padrao) {
        this.padrao = padrao;
    }


}
