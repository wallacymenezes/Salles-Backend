package com.projects.sallesregister.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data da venda é obrigatória.")
    private LocalDate dataVenda;

    @NotBlank(message = "O nome do cliente é obrigatório.")
    private String nomeCliente;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 dígitos.")
    private String cpf;

    @NotBlank(message = "O WhatsApp/celular é obrigatório.")
    private String whatsapp;

    private String telefone;

    @NotBlank(message = "A bandeira do cartão é obrigatória.")
    private String bandeiraCartao;

    @NotBlank(message = "O nome do banco do cartão é obrigatório.")
    private String nomeBancoCartao;

    @NotNull(message = "O valor da venda é obrigatório.")
    @DecimalMin(value = "0.00", inclusive = false, message = "O valor da venda deve ser maior que zero.")
    private BigDecimal valorVenda;

    @NotBlank(message = "O status da venda é obrigatório.")
    private String statusVenda;

    private String observacoes;

    @NotBlank(message = "O nome do vendedor é obrigatório.")
    private String vendedor;

    @NotNull(message = "O tipo de cliente (PF ou PJ) é obrigatório.")
    private TipoCliente tipoCliente;

    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter 14 dígitos.")
    private String cnpj;

    public enum TipoCliente {
        PF, PJ
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getBandeiraCartao() {
        return bandeiraCartao;
    }

    public void setBandeiraCartao(String bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }

    public String getNomeBancoCartao() {
        return nomeBancoCartao;
    }

    public void setNomeBancoCartao(String nomeBancoCartao) {
        this.nomeBancoCartao = nomeBancoCartao;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public String getStatusVenda() {
        return statusVenda;
    }

    public void setStatusVenda(String statusVenda) {
        this.statusVenda = statusVenda;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
