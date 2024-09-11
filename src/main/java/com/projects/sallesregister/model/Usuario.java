package com.projects.sallesregister.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The atribute name is mandatory")
    @Size(min = 3, max = 255, message = "The name must have at least 3 characters to 255 characters")
    private String nome;

    @Schema(example = "email@email.com.br")
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String usuario;

    @NotBlank(message = "The atribute password is mandatory")
    @Size(min = 8, message = "The atribute password must have at least 8 characters")
    private String senha;

    @Size(max = 1000, message = "The photo atribute has a limit of 1000 characters")
    private String foto;

    @ManyToOne()
    @JoinColumn(name = "cargo_id")
    @JsonIgnoreProperties("usuarios")
    private Cargo cargo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vendedor", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("usuario")
    private List<Venda> vendas;

    // Metodos construtores

    public Usuario(Long id, String nome, String usuario, String senha, String foto, Cargo cargo) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.foto = foto;
        this.cargo = cargo;
    }

    public Usuario() { }

    // Getters e Setters

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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }
}
