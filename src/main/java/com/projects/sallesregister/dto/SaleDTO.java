package com.projects.sallesregister.dto;

import com.projects.sallesregister.entities.Sale;
import com.projects.sallesregister.entities.User;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;

public class SaleDTO {

	private Long id;

	@NotNull(message = "Sale date is required.")
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
	private Sale.ClientType clientType;

	private String cnpj;

	private User user;

	public SaleDTO(Sale sale, User user) {
	}

	public SaleDTO(Long id, Date saleDate, String clientName, String cpf, String whatsapp, String phone, String cardBrand, String cardBankName, BigDecimal saleValue, String saleStatus, String notes, Sale.ClientType clientType, String cnpj, User user) {
		this.id = id;
		this.saleDate = saleDate;
		this.clientName = clientName;
		this.cpf = cpf;
		this.whatsapp = whatsapp;
		this.phone = phone;
		this.cardBrand = cardBrand;
		this.cardBankName = cardBankName;
		this.saleValue = saleValue;
		this.saleStatus = saleStatus;
		this.notes = notes;
		this.clientType = clientType;
		this.cnpj = cnpj;
		this.user = user;
	}

	public SaleDTO(Sale entity) {
		this.id = entity.getId();
		this.saleDate = entity.getSaleDate();
		this.clientName = entity.getClientName();
		this.cpf = entity.getCpf();
		this.whatsapp = entity.getWhatsapp();
		this.phone = entity.getPhone();
		this.cardBrand = entity.getCardBrand();
		this.cardBankName = entity.getCardBankName();
		this.saleValue = entity.getSaleValue();
		this.saleStatus = entity.getSaleStatus();
		this.notes = entity.getNotes();
		this.clientType = entity.getClientType();
		this.cnpj = entity.getCnpj();
		this.user = entity.getUser();
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

	public Sale.ClientType getClientType() {
		return clientType;
	}

	public void setClientType(Sale.ClientType clientType) {
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
