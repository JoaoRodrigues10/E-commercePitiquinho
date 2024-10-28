
package br.loja.pitiquinho.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "tb_enderecos_entrega")
public class EnderecoEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 00000-000")
    private String cep;

    @NotNull
    private String logradouro;

    @NotNull
    private String numero;

    private String complemento;

    @NotNull
    private String bairro;

    @NotNull
    private String cidade;

    @NotNull
    private String uf;

    @ManyToOne
    @JoinColumn(name = "usuario_fk", nullable = false)
    private Usuario usuario;


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 00000-000") String getCep() {
        return cep;
    }

    public void setCep(@NotNull @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 00000-000") String cep) {
        this.cep = cep;
    }

    public @NotNull String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(@NotNull String logradouro) {
        this.logradouro = logradouro;
    }

    public @NotNull String getNumero() {
        return numero;
    }

    public void setNumero(@NotNull String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public @NotNull String getBairro() {
        return bairro;
    }

    public void setBairro(@NotNull String bairro) {
        this.bairro = bairro;
    }

    public @NotNull String getCidade() {
        return cidade;
    }

    public void setCidade(@NotNull String cidade) {
        this.cidade = cidade;
    }

    public @NotNull String getUf() {
        return uf;
    }

    public void setUf(@NotNull String uf) {
        this.uf = uf;
    }
}
