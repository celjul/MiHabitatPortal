package com.bstmexico.mihabitat.pagostdc.feenicia.dto;

/**
 * Created by Pegasus on 28/12/2016.
 */
public class PagoExterno extends Pago {

    private String key;

    private String iv;

    private String keyRequest;

    private String ivRequest;

    private String affiliation;

    private String merchant;

    private String usuario;

    private String ref;

    private String email;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getKeyRequest() {
        return keyRequest;
    }

    public void setKeyRequest(String keyRequest) {
        this.keyRequest = keyRequest;
    }

    public String getIvRequest() {
        return ivRequest;
    }

    public void setIvRequest(String ivRequest) {
        this.ivRequest = ivRequest;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
