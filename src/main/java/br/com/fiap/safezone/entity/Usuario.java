package br.com.fiap.safezone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    private String senhaHash;

    @Column(unique = true)
    private String cpf;

    private String telefone;

    private String tipoSanguineo;

    private LocalDate dataNascimento;

    @JsonIgnore
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Endereco endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Alerta> alertas;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public java.util.List<Alerta> getAlertas() {
        return alertas;
    }

    public void setAlertas(java.util.List<Alerta> alertas) {
        this.alertas = alertas;
    }
}
