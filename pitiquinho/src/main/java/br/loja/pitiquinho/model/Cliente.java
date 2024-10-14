package br.loja.pitiquinho.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, message = "O nome completo deve ter no mínimo 3 letras em cada palavra.")
    private String nomeCompleto;

    @NotNull
    @Past(message = "A data de nascimento deve ser no passado.")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @NotNull
    private String genero;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "\\d{11}", message = "O CPF deve ter 11 dígitos.")
    private String cpf;

    @NotNull
    @Column(unique = true)
    @Email(message = "Email inválido.")
    private String email;

    @NotNull
    private String senha;

    @NotNull
    private String enderecoFaturamento;

    private String complemento;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cliente_id")
    private List<Endereco> enderecosEntrega = new ArrayList<>();

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEnderecoFaturamento() { return enderecoFaturamento; }
    public void setEnderecoFaturamento(String enderecoFaturamento) { this.enderecoFaturamento = enderecoFaturamento; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public List<Endereco> getEnderecosEntrega() { return enderecosEntrega; }
    public void setEnderecosEntrega(List<Endereco> enderecosEntrega) { this.enderecosEntrega = enderecosEntrega; }
}
