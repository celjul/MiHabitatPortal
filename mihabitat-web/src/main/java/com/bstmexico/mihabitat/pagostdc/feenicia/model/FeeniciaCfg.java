package com.bstmexico.mihabitat.pagostdc.feenicia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.bstmexico.mihabitat.condominios.model.PagoTDC;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Entidad que representa la configuración de Feenicia por Condominio.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
@Entity
@Table(name = "tfeenicia")
@PrimaryKeyJoinColumn(name = "NIdFeenicia")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class FeeniciaCfg extends PagoTDC {

	private static final long serialVersionUID = 4976487054182874222L;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VMerchantRequestIV", nullable = false)
	private String merchantRequestIV;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VMerchantRequestKEY", nullable = false)
	private String merchantRequestKEY;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VMerchantRequestSignatureIV", nullable = false)
	private String merchantRequestSignatureIV;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VMerchantRequestSignatureKey", nullable = false)
	private String merchantRequestSignatureKey;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VMerchantResponseIV", nullable = false)
	private String merchantResponseIV;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VMerchantResponseKEY", nullable = false)
	private String merchantResponseKEY;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VMerchantResponseSignatureIV", nullable = false)
	private String merchantResponseSignatureIV;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VMerchantResponseSignatureKey", nullable = false)
	private String merchantResponseSignatureKey;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VAffiliationFeenicia", nullable = false)
	private String affiliationFeenicia;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VUserFeenicia", nullable = false)
	private String userFeenicia;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VMerchantFeenicia", nullable = false)
	private String merchantFeenicia;

	@NotNull
	@Size(min = 1, max = 64)
	@Column(length = 64, name = "VHost", nullable = false)
	private String host;

	public String getMerchantRequestIV() {
		return merchantRequestIV;
	}

	public void setMerchantRequestIV(String merchantRequestIV) {
		this.merchantRequestIV = merchantRequestIV;
	}

	public String getMerchantRequestKEY() {
		return merchantRequestKEY;
	}

	public void setMerchantRequestKEY(String merchantRequestKEY) {
		this.merchantRequestKEY = merchantRequestKEY;
	}

	public String getMerchantRequestSignatureIV() {
		return merchantRequestSignatureIV;
	}

	public void setMerchantRequestSignatureIV(
			String merchantRequestSignatureIV) {
		this.merchantRequestSignatureIV = merchantRequestSignatureIV;
	}

	public String getMerchantRequestSignatureKey() {
		return merchantRequestSignatureKey;
	}

	public void setMerchantRequestSignatureKey(
			String merchantRequestSignatureKey) {
		this.merchantRequestSignatureKey = merchantRequestSignatureKey;
	}

	public String getMerchantResponseIV() {
		return merchantResponseIV;
	}

	public void setMerchantResponseIV(String merchantResponseIV) {
		this.merchantResponseIV = merchantResponseIV;
	}

	public String getMerchantResponseKEY() {
		return merchantResponseKEY;
	}

	public void setMerchantResponseKEY(String merchantResponseKEY) {
		this.merchantResponseKEY = merchantResponseKEY;
	}

	public String getMerchantResponseSignatureIV() {
		return merchantResponseSignatureIV;
	}

	public void setMerchantResponseSignatureIV(
			String merchantResponseSignatureIV) {
		this.merchantResponseSignatureIV = merchantResponseSignatureIV;
	}

	public String getMerchantResponseSignatureKey() {
		return merchantResponseSignatureKey;
	}

	public void setMerchantResponseSignatureKey(
			String merchantResponseSignatureKey) {
		this.merchantResponseSignatureKey = merchantResponseSignatureKey;
	}

	public String getAffiliationFeenicia() {
		return affiliationFeenicia;
	}

	public void setAffiliationFeenicia(String affiliationFeenicia) {
		this.affiliationFeenicia = affiliationFeenicia;
	}

	public String getUserFeenicia() {
		return userFeenicia;
	}

	public void setUserFeenicia(String userFeenicia) {
		this.userFeenicia = userFeenicia;
	}

	public String getMerchantFeenicia() {
		return merchantFeenicia;
	}

	public void setMerchantFeenicia(String merchantFeenicia) {
		this.merchantFeenicia = merchantFeenicia;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
