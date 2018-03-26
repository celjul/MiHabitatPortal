package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

/**
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2016
 */
public class SavedSale {

	private String orderId;

	private Integer transactionId;

	private String authnum;

	private String transactionDate;

	private String panTermination;

	private String affiliation;

	private String merchant;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public String getAuthnum() {
		return authnum;
	}

	public void setAuthnum(String authnum) {
		this.authnum = authnum;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getPanTermination() {
		return panTermination;
	}

	public void setPanTermination(String panTermination) {
		this.panTermination = panTermination;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	@Override
	public String toString() {
		return "SavedSale [orderId=" + orderId + ", transactionId="
				+ transactionId + ", authnum=" + authnum + ", transactionDate="
				+ transactionDate + ", panTermination=" + panTermination
				+ ", affiliation=" + affiliation + ", merchant=" + merchant
				+ "]";
	}
}
