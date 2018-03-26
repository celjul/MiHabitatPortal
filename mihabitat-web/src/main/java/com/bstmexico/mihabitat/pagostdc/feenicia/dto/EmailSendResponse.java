package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

/**
 * Created by Pegasus on 02/01/2017.
 */
public class EmailSendResponse {

    private String responseCode;

    private String Base64Pdf;

    private String Url;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getBase64Pdf() {
        return Base64Pdf;
    }

    public void setBase64Pdf(String base64Pdf) {
        Base64Pdf = base64Pdf;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public String toString() {
        return "EmailSendResponse{" +
                "responseCode='" + responseCode + '\'' +
                ", Base64Pdf='" + Base64Pdf + '\'' +
                ", Url='" + Url + '\'' +
                '}';
    }
}
