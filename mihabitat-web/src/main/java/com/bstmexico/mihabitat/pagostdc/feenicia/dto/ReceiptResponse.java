package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

/**
 * Created by Pegasus on 02/01/2017.
 */
public class ReceiptResponse {

    private String receiptId;

    private String responseCode;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    @Override
    public String toString() {
        return "ReceiptResponse{" +
                "receiptId=" + receiptId +
                ", responseCode='" + responseCode + '\'' +
                '}';
    }
}
