package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

import java.math.BigDecimal;

/**
 * Created by Pegasus on 02/01/2017.
 */
public class CreateReceipt {

    private Integer orderId;

    private Integer transactionId;

    private BigDecimal total;

    private String legalEntityName;

    private String merchantStreetNumColony;

    private String merchantCityStateZipCode;

    private String affiliationId;

    private String lastDigitsCard;

    private String base64ImgSignature;

    private String authNumber;

    private String operationId;

    private String controlNumber;

    private String nameInCard;

    private String descriptionCard;

    private String receiptDateTime;

    private String aid;

    private String arqc;

    private String mensajeComercio;

    private String clientLogoBase64Data;

    private String clientLogoDataType;

    private Boolean sendUrlByMail;

    private BigDecimal propina;

    private String strMerchantId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getLegalEntityName() {
        return legalEntityName;
    }

    public void setLegalEntityName(String legalEntityName) {
        this.legalEntityName = legalEntityName;
    }

    public String getMerchantStreetNumColony() {
        return merchantStreetNumColony;
    }

    public void setMerchantStreetNumColony(String merchantStreetNumColony) {
        this.merchantStreetNumColony = merchantStreetNumColony;
    }

    public String getMerchantCityStateZipCode() {
        return merchantCityStateZipCode;
    }

    public void setMerchantCityStateZipCode(String merchantCityStateZipCode) {
        this.merchantCityStateZipCode = merchantCityStateZipCode;
    }

    public String getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(String affiliationId) {
        this.affiliationId = affiliationId;
    }

    public String getLastDigitsCard() {
        return lastDigitsCard;
    }

    public void setLastDigitsCard(String lastDigitsCard) {
        this.lastDigitsCard = lastDigitsCard;
    }

    public String getBase64ImgSignature() {
        return base64ImgSignature;
    }

    public void setBase64ImgSignature(String base64ImgSignature) {
        this.base64ImgSignature = base64ImgSignature;
    }

    public String getAuthNumber() {
        return authNumber;
    }

    public void setAuthNumber(String authNumber) {
        this.authNumber = authNumber;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public String getNameInCard() {
        return nameInCard;
    }

    public void setNameInCard(String nameInCard) {
        this.nameInCard = nameInCard;
    }

    public String getDescriptionCard() {
        return descriptionCard;
    }

    public void setDescriptionCard(String descriptionCard) {
        this.descriptionCard = descriptionCard;
    }

    public String getReceiptDateTime() {
        return receiptDateTime;
    }

    public void setReceiptDateTime(String receiptDateTime) {
        this.receiptDateTime = receiptDateTime;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getArqc() {
        return arqc;
    }

    public void setArqc(String arqc) {
        this.arqc = arqc;
    }

    public String getMensajeComercio() {
        return mensajeComercio;
    }

    public void setMensajeComercio(String mensajeComercio) {
        this.mensajeComercio = mensajeComercio;
    }

    public String getClientLogoBase64Data() {
        return clientLogoBase64Data;
    }

    public void setClientLogoBase64Data(String clientLogoBase64Data) {
        this.clientLogoBase64Data = clientLogoBase64Data;
    }

    public String getClientLogoDataType() {
        return clientLogoDataType;
    }

    public void setClientLogoDataType(String clientLogoDataType) {
        this.clientLogoDataType = clientLogoDataType;
    }

    public Boolean getSendUrlByMail() {
        return sendUrlByMail;
    }

    public void setSendUrlByMail(Boolean sendUrlByMail) {
        this.sendUrlByMail = sendUrlByMail;
    }

    public BigDecimal getPropina() {
        return propina;
    }

    public void setPropina(BigDecimal propina) {
        this.propina = propina;
    }

    public String getStrMerchantId() {
        return strMerchantId;
    }

    public void setStrMerchantId(String strMerchantId) {
        this.strMerchantId = strMerchantId;
    }

    @Override
    public String toString() {
        return "CreateReceipt{" +
                "orderId='" + orderId + '\'' +
                ", transactionId=" + transactionId +
                ", total=" + total +
                ", legalEntityName='" + legalEntityName + '\'' +
                ", merchantStreetNumColony='" + merchantStreetNumColony + '\'' +
                ", merchantCityStateZipCode='" + merchantCityStateZipCode + '\'' +
                ", affiliationId='" + affiliationId + '\'' +
                ", lastDigitsCard='" + lastDigitsCard + '\'' +
                ", base64ImgSignature='" + base64ImgSignature + '\'' +
                ", authNumber='" + authNumber + '\'' +
                ", operationId='" + operationId + '\'' +
                ", controlNumber='" + controlNumber + '\'' +
                ", nameInCard='" + nameInCard + '\'' +
                ", descriptionCard='" + descriptionCard + '\'' +
                ", receiptDateTime='" + receiptDateTime + '\'' +
                ", aid='" + aid + '\'' +
                ", arqc='" + arqc + '\'' +
                ", mensajeComercio='" + mensajeComercio + '\'' +
                ", clientLogoBase64Data='" + clientLogoBase64Data + '\'' +
                ", clientLogoDataType='" + clientLogoDataType + '\'' +
                ", sendUrlByMail=" + sendUrlByMail +
                ", propina=" + propina +
                ", strMerchantId='" + strMerchantId + '\'' +
                '}';
    }
}
