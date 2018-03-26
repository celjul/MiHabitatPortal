package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

import java.math.BigDecimal;

/**
 * Created by Pegasus on 28/12/2016.
 */
public class RespuestaExterna {

    private String responseCode;

    private String descripcion;

    private String authnum;

    private Integer transactionId;

    private ManualSaleMerchantResponse merchant;

    private ManualSaleCardResponse card;

    private BigDecimal amount;

    private ManualSaleBankResponse issuerBank;

    private ManualSaleBankResponse acquirerBank;

    private Boolean approved;

    private Long transactionDate;

    private String receiptId;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAuthnum() {
        return authnum;
    }

    public void setAuthnum(String authnum) {
        this.authnum = authnum;
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

    public Long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Long transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "SavedSaleResponse [descripcion=" + descripcion + ", responseCode="
                + responseCode + "]";
    }

}
