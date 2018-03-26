package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

import java.math.BigDecimal;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class ManualSaleResponse {

	private String affiliation;

	private String authnum;

	private String responseCode;

	private Integer transactionId;

	private ManualSaleMerchantResponse merchant;

	private ManualSaleCardResponse card;

	private BigDecimal amount;

	private Double tip;

	private ManualSaleCurrencyResponse currency;

	private ManualSaleBankResponse issuerBank;

	private ManualSaleBankResponse acquirerBank;

	private Boolean approved;

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getAuthnum() {
		return authnum;
	}

	public void setAuthnum(String authnum) {
		this.authnum = authnum;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public ManualSaleMerchantResponse getMerchant() {
		return merchant;
	}

	public void setMerchant(ManualSaleMerchantResponse merchant) {
		this.merchant = merchant;
	}

	public ManualSaleCardResponse getCard() {
		return card;
	}

	public void setCard(ManualSaleCardResponse card) {
		this.card = card;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Double getTip() {
		return tip;
	}

	public void setTip(Double tip) {
		this.tip = tip;
	}

	public ManualSaleCurrencyResponse getCurrency() {
		return currency;
	}

	public void setCurrency(ManualSaleCurrencyResponse currency) {
		this.currency = currency;
	}

	public ManualSaleBankResponse getIssuerBank() {
		return issuerBank;
	}

	public void setIssuerBank(ManualSaleBankResponse issuerBank) {
		this.issuerBank = issuerBank;
	}

	public ManualSaleBankResponse getAcquirerBank() {
		return acquirerBank;
	}

	public void setAcquirerBank(ManualSaleBankResponse acquirerBank) {
		this.acquirerBank = acquirerBank;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return "ManualSaleResponse [affiliation=" + affiliation + ", authnum="
				+ authnum + ", responseCode=" + responseCode
				+ ", transactionId=" + transactionId + ", merchant=" + merchant
				+ ", card=" + card + ", amount=" + amount + ", tip=" + tip
				+ ", currency=" + currency + ", issuerBank=" + issuerBank
				+ ", acquirerBank=" + acquirerBank + ", approved=" + approved
				+ "]";
	}
}
