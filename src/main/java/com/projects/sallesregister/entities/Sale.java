package com.projects.sallesregister.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tb_sales")
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date saleDate;

	@NotBlank(message = "Client name is required.")
	private String clientName;

	@NotBlank(message = "CPF is required.")
	@Pattern(regexp = "\\d{11}", message = "CPF must be 11 digits.")
	private String cpf;

	@NotBlank(message = "WhatsApp is required.")
	private String whatsapp;

	private String phone;

	@NotBlank(message = "Card brand is required.")
	private String cardBrand;

	@NotBlank(message = "Bank name is required.")
	private String cardBankName;

	@NotNull(message = "Sale value is required.")
	@DecimalMin(value = "0.0", inclusive = false, message = "Sale value must be greater than zero.")
	private BigDecimal saleValue;

	@NotBlank(message = "Sale status is required.")
	private String saleStatus;

	private String notes;

	@NotNull(message = "Client type (PF or PJ) is required.")
	@Enumerated(EnumType.STRING)
	private ClientType clientType;

	private String cnpj;

	@ManyToOne
	@JsonIgnoreProperties("sales")
	private User user;

	public enum ClientType {
		PF, PJ
	}

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCardBrand() {
		return cardBrand;
	}

	public void setCardBrand(String cardBrand) {
		this.cardBrand = cardBrand;
	}

	public String getCardBankName() {
		return cardBankName;
	}

	public void setCardBankName(String cardBankName) {
		this.cardBankName = cardBankName;
	}

	public BigDecimal getSaleValue() {
		return saleValue;
	}

	public void setSaleValue(BigDecimal saleValue) {
		this.saleValue = saleValue;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
