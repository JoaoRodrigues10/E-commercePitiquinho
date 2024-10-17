package br.loja.pitiquinho.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private Long id;

    @Column(name = "ds_nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "ds_cpf", nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(name = "ds_email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "ds_senha", nullable = false, length = 255)
    private String senha;

    @Column(name = "bo_status", nullable = false)
    private Boolean status = false;

    @Column(name = "ds_grupo", nullable = false, length = 20)
    private String grupo;

    @Column(name = "dt_nascimento", nullable = false)
    private String dataNascimento;

    @Column(name = "ds_genero", nullable = false, length = 20)
    private String genero;

    @Column(name = "ds_cep_faturamento", nullable = false, length = 9)
    private String cepFaturamento;

    @Column(name = "ds_logradouro_faturamento", nullable = false, length = 150)
    private String logradouroFaturamento;

    @Column(name = "ds_numero_faturamento", nullable = false, length = 10)
    private String numeroFaturamento;

    @Column(name = "ds_complemento_faturamento", length = 50)
    private String complementoFaturamento;

    @Column(name = "ds_bairro_faturamento", nullable = false, length = 100)
    private String bairroFaturamento;

    @Column(name = "ds_cidade_faturamento", nullable = false, length = 100)
    private String cidadeFaturamento;

    @Column(name = "ds_uf_faturamento", nullable = false, length = 10)
    private String ufFaturamento;

    // Novo campo para telefone
    @Column(name = "ds_telefone", nullable = false, length = 15) // Ajuste o comprimento conforme necessário
    private String telefone;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(grupo));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }


    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCepFaturamento() {
        return cepFaturamento;
    }

    public void setCepFaturamento(String cepFaturamento) {
        this.cepFaturamento = cepFaturamento;
    }

    public String getLogradouroFaturamento() {
        return logradouroFaturamento;
    }

    public void setLogradouroFaturamento(String logradouroFaturamento) {
        this.logradouroFaturamento = logradouroFaturamento;
    }

    public String getNumeroFaturamento() {
        return numeroFaturamento;
    }

    public void setNumeroFaturamento(String numeroFaturamento) {
        this.numeroFaturamento = numeroFaturamento;
    }

    public String getComplementoFaturamento() {
        return complementoFaturamento;
    }

    public void setComplementoFaturamento(String complementoFaturamento) {
        this.complementoFaturamento = complementoFaturamento;
    }

    public String getBairroFaturamento() {
        return bairroFaturamento;
    }

    public void setBairroFaturamento(String bairroFaturamento) {
        this.bairroFaturamento = bairroFaturamento;
    }

    public String getCidadeFaturamento() {
        return cidadeFaturamento;
    }

    public void setCidadeFaturamento(String cidadeFaturamento) {
        this.cidadeFaturamento = cidadeFaturamento;
    }

    public String getUfFaturamento() {
        return ufFaturamento;
    }

    public void setUfFaturamento(String ufFaturamento) {
        this.ufFaturamento = ufFaturamento;
    }

    // Getters e Setters para o campo telefone
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
